package domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AttendancesFile {
    public static final String DELIMITER = ",";

    public Map<String, Attendances> loadInitialAttendances(Path path) {
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

        return crewAttendances;
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
