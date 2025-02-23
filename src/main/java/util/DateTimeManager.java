package util;

import static util.Constants.HOLIDAY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeManager {
    private static final String TIME_UNIT = ":";
    private final LocalDate today;

    public DateTimeManager(int year, int month, int day) {
        this.today = LocalDate.of(year, month, day);
    }

    public static boolean isHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY
                || dayOfWeek == DayOfWeek.SUNDAY
                || date.getDayOfMonth() == HOLIDAY;
    }

    public LocalDate getToday() {
        return today;
    }

    public LocalDate getYesterday() {
        return today.minusDays(1);
    }

    public LocalDateTime getDateTime(String formattedTime) {
        return LocalDateTime.of(today, getTime(formattedTime));

    }

    public LocalDateTime getModifiedDate(String modifyDay, String modifyTime) {
        return LocalDateTime.of(getDate(modifyDay), getTime(modifyTime));
    }

    private LocalDate getDate(String day) {
        return LocalDate.of(today.getYear(),
                today.getMonthValue(),
                Integer.parseInt(day));
    }

    private LocalTime getTime(String formattedTime) {
        return LocalTime.of(
                Integer.parseInt(formattedTime.split(TIME_UNIT)[0]),
                Integer.parseInt(formattedTime.split(TIME_UNIT)[1]));
    }
}
