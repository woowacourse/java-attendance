package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CsvReader {
    private static final Path FILE_PATH = Paths.get("src", "main", "resources", "attendances.csv");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String DELIMITER = ",";

    public Map<String, List<LocalDateTime>> readAttendanceFile() {
        Map<String, List<LocalDateTime>> attendanceHistories = new HashMap<>();
        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            lines.skip(1).forEach(line -> processLine(line, attendanceHistories));
        } catch (IOException e) {
            throw new IllegalStateException("출석 데이터 파일을 읽는 중 오류가 발생했습니다: " + e);
        }
        return attendanceHistories;
    }

    private void processLine(String line, Map<String, List<LocalDateTime>> attendanceHistories) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 데이터 형식입니다: " + line);
        }
        String username = parts[0].trim();
        LocalDateTime dateTime = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        attendanceHistories.computeIfAbsent(username, k -> new ArrayList<>()).add(dateTime);
    }
}