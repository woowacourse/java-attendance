package util;

import java.io.FileNotFoundException;
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

public class CsvAttendanceDataReader implements AttendanceDataReader {
    private static final Path FILE_PATH = Paths.get("src", "main", "resources", "attendances.csv");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String DELIMITER = ",";
    private static final int USERNAME_INDEX = 0;
    private static final int TIME_INDEX = 1;
    private static final int LIMIT_INDEX = 2;

    @Override
    public Map<String, List<LocalDateTime>> loadAttendanceData() {
        Map<String, List<LocalDateTime>> crewsMap = new HashMap<>();
        try (Stream<String> lines = Files.lines(FILE_PATH)) {
            readLines(lines, crewsMap);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("출석 데이터 파일을 찾을 수 없습니다: " + e);
        } catch (IOException e) {
            throw new IllegalStateException("출석 데이터 파일을 읽는 중 오류가 발생했습니다: " + e);
        }
        return crewsMap;
    }

    private void readLines(Stream<String> lines, Map<String, List<LocalDateTime>> crewsMap) {
        lines.skip(1)
                .forEach(line -> processLine(line, crewsMap));
    }

    private void processLine(String line, Map<String, List<LocalDateTime>> crewsMap) {
        String[] parts = line.split(DELIMITER);
        if (parts.length != LIMIT_INDEX) {
            throw new IllegalArgumentException("[ERROR] 잘못된 데이터 형식입니다: ");
        }
        String username = parts[USERNAME_INDEX].trim();
        LocalDateTime dateTime = LocalDateTime.parse(parts[TIME_INDEX].trim(), FORMATTER);
        crewsMap.computeIfAbsent(username, k -> new ArrayList<>()).add(dateTime);
    }
}
