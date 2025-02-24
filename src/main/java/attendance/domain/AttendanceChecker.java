package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceChecker {

    public static void checkCampusHour(final int hour, final int minute) {
        if (hour < 8 || (hour >= 23 && minute > 0)) {
            throw new IllegalArgumentException("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public static void validateCampusDay(final int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (!isCampusDay(day)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", localDate.getMonthValue(), day,
                            dayOfWeek.getDisplayName(
                                    TextStyle.FULL, Locale.KOREA)));
        }
    }

    public static boolean isCampusDay(final int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return !(isHoliday(day) || isWeekend(dayOfWeek));
    }

    private static boolean isHoliday(final int day) {
        return day == 25;
    }

    private static boolean isWeekend(final DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
