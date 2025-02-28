package util;

import exception.ErrorException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Convertor {

    public static String convertDayOfWeekToKorean(DayOfWeek dayOfWeek) {
        List<String> koreanDayOfWeek = List.of("월", "화", "수", "목", "금", "토", "일");
        return koreanDayOfWeek.get(dayOfWeek.ordinal() % 7);
    }

    public static LocalDateTime convertStringToDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static int convertDayToNumber(String day) {
        try {
            int number = Integer.parseInt(day);
            validateDayRange(number);
            return number;
        } catch (NumberFormatException e) {
            throw new ErrorException("숫자여야 합니다.");
        }
    }

    public static LocalTime convertStringToTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new ErrorException("올바른 시간 형식이 아닙니다.");
        }
    }

    private static void validateDayRange(int day) throws ErrorException {
        if (day < 1 || day > 31) {
            throw new ErrorException("1 이상, 31 이하의 숫자여야 합니다.");
        }
    }
}
