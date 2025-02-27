package domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AttendancesFile {
    public static final String DELIMITER = ",";

    public Map<String, Attendances> loadInitialAttendances(Path path, LocalDate today) {
        Map<String, Attendances> crewAttendances = new HashMap<>();

        for (String line : readLines(path)) {
            String[] splitLines = line.split(DELIMITER);
            String nickname = splitLines[0];
            LocalDateTime dateTime = convertStringToDateTime(splitLines);
            if (crewAttendances.containsKey(nickname)) {
                crewAttendances.get(nickname).addAttendance(dateTime);
                continue;
            }
            Attendances attendances = new Attendances();
            attendances.addAttendance(dateTime);
            crewAttendances.put(nickname, attendances);
        }

        for(Attendances attendances : crewAttendances.values()) {
            addAllAbsents(today.getDayOfMonth(), attendances.getRecords());
        }
        return crewAttendances;
    }

    private void addAllAbsents(int today, List<Attendance> records) {
        for(int day = 1; day < today; day++) {
            addAbsent(records, day);
        }
    }

    private static void addAbsent(List<Attendance> records, int day) {
        if(DayType.calculateDayType(day) != DayType.WEEKDAY) {
            return;
        }
        if(records.stream()
                .anyMatch(attendance -> attendance.isSameDay(day))) {
            return;
        }

        records.add(new Attendance(LocalDateTime.of(2024, 12, day, 23, 59)));
    }

    private List<String> readLines(Path path) {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.skip(1)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .toList();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private LocalDateTime convertStringToDateTime(String[] splitLines) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(splitLines[1], formatter);
    }
}
