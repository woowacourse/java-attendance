package util;

import domain.WorkDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceFileParser {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private AttendanceFileParser() {
    }

    public static Set<String> loadCrewNames(String filePath) {
        return FileDataLoader.loadLines(filePath)
                .stream()
                .skip(1)
                .map(line -> line.split(",", -1)[0].trim())
                .collect(Collectors.toSet());
    }

    public static Map<String, List<WorkDateTime>> loadAttendanceRecords(String filePath) {
        Map<String, List<WorkDateTime>> attendanceRecords = new HashMap<>();

        FileDataLoader.loadLines(filePath)
                .stream()
                .skip(1)
                .map(line -> line.split(",", -1))
                .filter(items -> items.length == 2)
                .forEach(items -> {
                    String name = items[0].trim();
                    WorkDateTime workDateTime = WorkDateTime.from(parseToDateTime(items[1].trim()));

                    attendanceRecords.computeIfAbsent(name, k -> new ArrayList<>()).add(workDateTime);
                });

        return attendanceRecords;
    }

    private static LocalDateTime parseToDateTime(String localDateTime) {
        try {
            return LocalDateTime.parse(localDateTime, DEFAULT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식: " + localDateTime);
        }
    }
}
