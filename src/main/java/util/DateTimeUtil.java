package util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import constant.FormatterConstant;

public class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    public static LocalTime convertToTime(String time) {
        try {
            return LocalTime.parse(time, FormatterConstant.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 형식이 아닙니다.");
        }
    }

    public static LocalDate convertToDate(LocalDate date, int day) {
        try {
            return date.withDayOfMonth(day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("올바른 날짜(일)이 아닙니다.");
        }
    }
}
