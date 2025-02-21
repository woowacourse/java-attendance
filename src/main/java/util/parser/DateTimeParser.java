package util.parser;

import static util.constant.Value.DATE_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeParser() {
    }

    public static LocalDateTime parseStringToDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    public static LocalDate parseIntegerToDate(int year, int month, int day) {
        String date = String.format(DATE_FORMAT, year, month, day);
        return LocalDate.parse(date, dateFormatter);
    }

}
