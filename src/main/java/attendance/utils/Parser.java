package attendance.utils;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static LocalDate toDateTime(String inputDate, LocalDate currentDate) {
        int numberDate = validateNumber(inputDate);
        validateDateRange(numberDate, currentDate);
        return currentDate.withDayOfMonth(numberDate);
    }

    private static int validateNumber(String inputDate) {
        try {
            return Integer.parseInt(inputDate);
        } catch (NumberFormatException numberFormatException) {
            throw CustomException.from(ErrorMessage.DATE_NUMBER_FORMAT);
        }
    }

    private static void validateDateRange(int inputDate, LocalDate currentDate) {
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        if (inputDate < 1 || inputDate > lastDayOfMonth) {
            throw CustomException.from(ErrorMessage.INVALID_DATE_RANGE);
        }
    }

    public static LocalDateTime toTime(String inputTime, LocalDate currentDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime time = LocalTime.parse(inputTime, formatter);
            return LocalDateTime.of(currentDate, time);
        } catch (DateTimeParseException dateTimeParseException) {
            throw CustomException.from(ErrorMessage.TIME_FORMAT_ERROR);
        }
    }

}
