package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public enum Holiday {
    SATURDAY(DayOfWeek.SATURDAY),
    SUNDAY(DayOfWeek.SUNDAY),
    CHRISTMAS(LocalDate.of(2024,12,25));

    private final DayOfWeek dayOfWeek;
    private final LocalDate holidayDate;

    Holiday(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        this.holidayDate = null;
    }

    Holiday(LocalDate holidayDate) {
        this.dayOfWeek = null;
        this.holidayDate = holidayDate;
    }
    public static boolean checkHoliday(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return true;
        }

        return Arrays.stream(Holiday.values())
                .anyMatch(holiday -> holiday.holidayDate != null && holiday.holidayDate.equals(date));
    }

    public static void validateHoliday(LocalDate localDate) {
        if (checkHoliday(localDate)){
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    localDate.getMonthValue(),
                    localDate.getDayOfMonth(),
                    localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)));
        }
    }



}
