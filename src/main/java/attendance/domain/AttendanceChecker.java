package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static java.time.DayOfWeek.MONDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceChecker {
    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);
    private static final LocalTime REGULAR_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    private static final int MINUTE_TO_PRESENT = 5;
    private static final int MINUTE_TO_LATE = 30;

    public static AttendanceStatus checkAttendance(LocalDateTime localDateTime) {
        final LocalTime time = localDateTime.toLocalTime() ;

        if (localDateTime.getDayOfWeek().equals(MONDAY)) {
            return checkMondayAttendance(time);
        }
        return checkRegularAttendance(time);
    }

    private static AttendanceStatus checkRegularAttendance(final LocalTime localTime) {
        if (localTime.isAfter(REGULAR_START_TIME.plusMinutes(MINUTE_TO_LATE))) {
            return ABSENCE;
        }
        if (localTime.isAfter(REGULAR_START_TIME.plusMinutes(MINUTE_TO_PRESENT))) {
            return LATENESS;
        }
        return PRESENT;
    }

    private static AttendanceStatus checkMondayAttendance(final LocalTime localTime) {
        if (localTime.isAfter(MONDAY_START_TIME.plusMinutes(MINUTE_TO_LATE))) {
            return ABSENCE;
        }
        if (localTime.isAfter(MONDAY_START_TIME.plusMinutes(MINUTE_TO_PRESENT))) {
            return LATENESS;
        }
        return PRESENT;
    }

    public static void validateCampusHour(final LocalTime localTime) {
        if (localTime.isBefore(CAMPUS_START_TIME) || localTime.isAfter(CAMPUS_END_TIME)) {
            throw new IllegalArgumentException("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public static void validateCampusDay(final LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (!isCampusDay(localDate)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                            localDate.getMonthValue(),
                            localDate.getDayOfMonth(),
                            dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREA)));
        }
    }

    public static boolean isCampusDay(final LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return !(isHoliday(localDate) || isWeekend(dayOfWeek));
    }

    private static boolean isHoliday(final LocalDate localDate) {
        return LocalDate.of(2024, 12, 25).equals(localDate);
    }

    private static boolean isWeekend(final DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
