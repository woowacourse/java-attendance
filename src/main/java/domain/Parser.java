package domain;

import java.time.DateTimeException;
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

    public static LocalDate parseInputDay(String input){
        try {
            return LocalDate.now().withDayOfMonth(Integer.parseInt(input));
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 날짜(일) 입력이 올바르지 않습니다.");
        }
    }

    public static LocalTime parseInputTime(String input){
        try {
            return LocalTime.parse(input);
        } catch (DateTimeException | NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 시간 입력이 올바르지 않습니다.");
        }
    }
}