package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendancesFileHandler {

    private static final Path ATTENDANCES_PATH = Path.of("src/main/resources/attendances.csv");
    private static Map<String, List<LocalDateTime>> attendances;

    public static Map<String, List<LocalDateTime>> generateAttendances()  {
        attendances = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(ATTENDANCES_PATH);
            readInformation(lines);
            return attendances;
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽어올 수 없습니다.");
        }
    }

    private static void readInformation(List<String> lines) {
        for (String line : lines.subList(1, lines.size())) {
            addInfoFromLine(line);
        }
    }

    private static void addInfoFromLine(String line) {
        String[] fields = line.split(",");
        String name = fields[0];
        attendances.computeIfAbsent(name, k -> new ArrayList<>());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(fields[1], formatter);
        attendances.get(name).add(dateTime);
    }
}
