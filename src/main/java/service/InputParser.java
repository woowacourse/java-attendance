package service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import view.ErrorCode;

public class InputParser {
    private static final String DATE_DELIMITER = " ";
    private static final String NAME_DELIMITER = ",";

    public static List<String> parseRecordToNameAndDate(String existedCrewRecord) {
        return Arrays.asList(existedCrewRecord.split(NAME_DELIMITER));
    }

    public static Map<LocalDate, LocalTime> parseDateToDayAndTime(String date) {
        List<String> dayAndTime = Arrays.asList(date.split(DATE_DELIMITER));
        LocalDate day = LocalDate.parse(dayAndTime.getFirst());
        LocalTime time = LocalTime.parse(dayAndTime.getLast());
        return Map.of(day, time);
    }

    public static LocalDate parseDayInput(String input) {
        try {
            return LocalDate.now().withDayOfMonth(Integer.parseInt(input));
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.DAY_INPUT_NOT_VALID.getFormat());
        }
    }

    public static LocalTime parseTimeInput(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.TIME_INPUT_NOT_VALID.getFormat());
        }
    }
}