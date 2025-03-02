package attendance.utils;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class Parser {
    public static LocalDateTime toDateTime(String inputDate, LocalDateTime currentDateTime) {
        int numberDate = validateNumber(inputDate);
        validateDateRange(numberDate, currentDateTime);
        return currentDateTime.withDayOfMonth(numberDate);
    }

    private static int validateNumber(String inputDate) {
        try {
            return Integer.parseInt(inputDate);
        } catch (NumberFormatException numberFormatException) {
            throw CustomException.from(ErrorMessage.DATE_NUMBER_FORMAT);
        }
    }

    private static void validateDateRange(int inputDate, LocalDateTime currentDateTime) {
        int year = currentDateTime.getYear();
        int month = currentDateTime.getMonthValue();
        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        if (inputDate < 1 || inputDate > lastDayOfMonth) {
            throw CustomException.from(ErrorMessage.INVALID_DATE_RANGE);
        }
    }
}
