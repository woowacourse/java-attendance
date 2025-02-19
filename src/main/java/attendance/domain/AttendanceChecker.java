package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceChecker {
    public static AttendanceStatus checkAttendance(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();

        if (localDateTime.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return checkAttendance(hour, minute);
        }
        return checkRegularAttendance(hour, minute);
    }

    public static AttendanceStatus checkRegularAttendance(final int hour, final int minute) {
        if (hour <= 10 && minute <= 5) {
            return PRESENT;
        }
        if (hour == 10 && (minute <= 30)) {
            return LATENESS;
        }
        return ABSENCE;
    }

    public static AttendanceStatus checkAttendance(final int hour, final int minute) {
        if (hour <= 13 && minute <= 5) {
            return PRESENT;
        }
        if (hour == 13 && (minute <= 30)) {
            return LATENESS;
        }
        return ABSENCE;
    }

    public static void checkCampusHour(final int hour, final int minute) {
        if (hour < 8 || (hour >= 23 && minute > 0)) {
            throw new IllegalArgumentException("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public static void checkCampusDay(final int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (isHoliday(day) || isWeekend(dayOfWeek)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", localDate.getMonthValue(), day,
                            dayOfWeek.getDisplayName(
                                    TextStyle.FULL, Locale.KOREA)));
        }
    }

    private static boolean isHoliday(final int day) {
        return day == 25;
    }

    private static boolean isWeekend(final DayOfWeek dayOfWeek) {
        return  dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
