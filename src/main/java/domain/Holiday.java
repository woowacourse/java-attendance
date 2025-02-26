package domain;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public enum Holiday {
    CHRISTMAS(12, 25),
    NEW_YEARS_DAY(1, 1);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static void validate(LocalDateTime attendanceTime) {
        validateWeekend(attendanceTime);
        validateHoliday(attendanceTime);
    }

    private static void validateWeekend(LocalDateTime attendanceTime) {
        if (isWeekend(attendanceTime.toLocalDate())) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일은 등교일이 아닙니다. 출석 확인은 등교일에만 가능합니다.",
                            attendanceTime.getMonthValue(), attendanceTime.getDayOfMonth())
            );
        }
    }

    private static void validateHoliday(LocalDateTime attendanceTime) {
        if (isHoliday(attendanceTime.toLocalDate())) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d월 %d일은 등교일이 아닙니다. 출석 확인은 등교일에만 가능합니다.",
                            attendanceTime.getMonthValue(), attendanceTime.getDayOfMonth())
            );
        }
    }

    public static boolean isHoliday(LocalDate attendanceTime) {
        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.isSameDate(attendanceTime))
                || isWeekend(attendanceTime);
    }

    private boolean isSameDate(LocalDate date) {
        return this.month == date.getMonthValue() && this.day == date.getDayOfMonth();
    }


    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY;
    }
}
