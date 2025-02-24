package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class AttendanceInitializer {

    public static List<String> readCrewAndAttendanceData(String data) {
        return Arrays.stream(data.split("\n")).toList();
    }

    public static List<String> extractUniqueCrewData(List<String> combinedData) {
        return combinedData.stream()
                .map(data -> data.split(",")[0])
                .distinct()
                .toList();
    }

    public static LocalDateTime parseLocalDateTimeFrom(String combinedData) {
        String dateAndTime = combinedData.split(",")[1];
        DateTimeFormatter yearMonthDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateAndTime, yearMonthDateTimeFormatter);
    }
}
