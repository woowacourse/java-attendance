package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceChecker {
    private static final String CAMPUS_CLOSED_ERROR_FORMAT = "%02d월 %02d일 %s은 등교일이 아닙니다.\n";
    private static final int CAMPUS_OPEN_HOUR = 8;
    private static final int CAMPUS_OPEN_MINUTE = 0;
    private static final int CAMPUS_CLOSE_HOUR = 23;
    private static final int CAMPUS_CLOSE_MINUTE = 0;

    public static void checkCampusOpen(LocalDate date, LocalTime time) {
        checkDate(date);
        checkTime(time);
    }

    private static void checkDate(LocalDate date) {
        if (isWeekend(date) || Holiday.isHoliday(date)) {
            throw new IllegalArgumentException(String.format(
                    CAMPUS_CLOSED_ERROR_FORMAT,
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    getDisplayName(date)
            ));
        }
    }

    private static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private static String getDisplayName(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
    }

    private static void checkTime(LocalTime time) {
        LocalTime openTime = LocalTime.of(CAMPUS_OPEN_HOUR, CAMPUS_OPEN_MINUTE);
        LocalTime closeTime = LocalTime.of(CAMPUS_CLOSE_HOUR, CAMPUS_CLOSE_MINUTE);

        if (time.isAfter(closeTime) || time.isBefore(openTime)) {
            throw new IllegalArgumentException("현재 캠퍼스 운영시간이 아닙니다.");
        }
    }


}
