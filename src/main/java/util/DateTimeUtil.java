package util;

import domain.Holiday;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    public static boolean isOffDay(LocalDate date) {
        boolean isWeekend = date.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        return isWeekend || isHoliday(date);
    }

    private static boolean isHoliday(LocalDate date) {
        return !Holiday.from(date).equals(Holiday.NONE);
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalTime convertToTime(String time) {
        try {
            return LocalTime.parse(time, Formatter.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 입력 형식이 아닙니다.");
        }
    }

    public static LocalDate convertToDate(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("올바른 날짜(일) 입력 형식이 아닙니다.");
        }
    }
}
