package utils;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import view.ErrorMessage;

public class ParsingUtils {
    public static List<String> parseRecordToNameAndDate(String existedRecords) {
        return Arrays.asList(existedRecords.split(","));
    }

    public static List<String> parseTimeLogToDateAndTime(String date) {
        return Arrays.asList(date.split(" "));
    }

    public static LocalTime parseTimeInput(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_TIME_INPUT_FORM_IS_NOT_VALID.getFormat());
        }
    }
}