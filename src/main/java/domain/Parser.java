package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Parser {
    public static List<String> parse(List<String> loadedData) {
        loadedData.removeFirst();
        return loadedData;
    }

    public static List<List<String>> parseName(List<String> removedData) {
        List<List<String>> result = new ArrayList<>();
        for (String data: removedData) {
            List<String> nameSeperatedData = Arrays.asList(data.split(","));
            result.add(nameSeperatedData);
        }
        return result;
    }

    public static Map<LocalDate, LocalTime> parseDate(String rawDateTime) {
        List<String> rawDateAndTime = Arrays.asList(rawDateTime.split(" "));
        String rawDate = rawDateAndTime.getFirst();
        String rawTime = rawDateAndTime.getLast();
        LocalDate date = LocalDate.parse(rawDate);
        LocalTime time = LocalTime.parse(rawTime);
        return Map.of(date, time);
    }
}