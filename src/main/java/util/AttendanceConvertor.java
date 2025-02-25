package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateTimeConvertor {

    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    public static LocalDate convertToDate(String raw) {
        List<String> dateAndTime = Arrays.stream(raw.split(" ")).toList();
        return LocalDate.parse(dateAndTime.get(DATE_INDEX), DATE_FORMATTER);
    }

    public static LocalTime convertToTime(String raw) {
        List<String> dateAndTime = Arrays.stream(raw.split(" ")).toList();
        return LocalTime.parse(dateAndTime.get(TIME_INDEX), TIME_FORMATTER);
    }
}
