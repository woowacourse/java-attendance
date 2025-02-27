package util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Convertor {

    public static String convertDayOfWeekToKorean(DayOfWeek dayOfWeek) {
        List<String> koreanDayOfWeek = List.of("일", "월", "화", "수", "목", "금", "토");
        return koreanDayOfWeek.get(dayOfWeek.ordinal() % 7);
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
