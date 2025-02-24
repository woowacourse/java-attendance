package util;

import domain.ErrorCode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Parser {

    private static final String DATE_DELIMITER = " ";
    private static final String NAME_DELIMITER = ",";
    private static final int NAME_PART_INDEX = 0;
    private static final int DATE_TIME_PART_INDEX = 1;

    public static List<String> parse(List<String> loadedData) {
        loadedData.removeFirst();
        return loadedData;
    }

    public static List<NameParsedData> parseName(List<String> removedData) {
        List<NameParsedData> result = new ArrayList<>();
        for (String data : removedData) {
            String[] splitData = data.split(NAME_DELIMITER);
            result.add(new NameParsedData(
                            splitData[NAME_PART_INDEX],
                            splitData[DATE_TIME_PART_INDEX]
                    )
            );
        }
        return result;
    }

    public static Map<LocalDate, LocalTime> parseDate(String rawDateTime) {
        List<String> rawDateAndTime = Arrays.asList(rawDateTime.split(DATE_DELIMITER));
        String rawDate = rawDateAndTime.getFirst();
        String rawTime = rawDateAndTime.getLast();
        LocalDate date = LocalDate.parse(rawDate);
        LocalTime time = LocalTime.parse(rawTime);
        return Map.of(date, time);
    }

    public static LocalDate parseInputDay(String input) {
        try {
            return LocalDate.now().withDayOfMonth(Integer.parseInt(input));
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.DAY_INPUT_NOT_VALID.getMessage());
        }
    }

    public static LocalTime parseInputTime(String input) {
        try {
            return LocalTime.parse(input);
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException(ErrorCode.TIME_INPUT_NOT_VALID.getMessage());
        }
    }

    public record NameParsedData(String namePart, String dateTimePart) {
    }
}
