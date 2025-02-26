package model;

import common.Common;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExistingAttendances {
    private final Map<String, List<LocalDateTime>> attendances;

    public static ExistingAttendances from(List<String> combinedData) {
        Map<String, List<LocalDateTime>> attendances = new HashMap<>();
        for (String data : combinedData) {
            String crewName = data.split(",")[0];
            LocalDateTime dateTime = parseAttendanceFrom(data);
            attendances.merge(crewName, new ArrayList<>(Arrays.asList(dateTime)), (newTime, existingTimes) -> {
                existingTimes.add(dateTime);
                return existingTimes;
            });
        }
        return new ExistingAttendances(attendances);
    }

    public ExistingAttendances(Map<String, List<LocalDateTime>> attendances) {
        this.attendances = attendances;
    }

    public static LocalDateTime parseAttendanceFrom(String combinedData) {
        String dateAndTime = combinedData.split(",")[1];
        DateTimeFormatter yearMonthDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime attendanceTime = LocalDateTime.parse(dateAndTime, yearMonthDateTimeFormatter);
        return attendanceTime;
    }

    public Map<String, List<LocalDateTime>> getAttendances() {
        return attendances;
    }
}
