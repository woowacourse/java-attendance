package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInputView {
    private static final String FILE_PATH = "/attendances.csv";
    private static final String CREW_DATETIME_SEPARATOR = ",";
    private static final int HEADER_ROW = 1;
    private static final int CREW_INDEX = 0;
    private static final int DATETIME_INDEX = 1;

    private static final DateTimeFormatter CSV_DATETIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Map<String, List<LocalDateTime>> readAttendanceFile() {
        Map<String, List<LocalDateTime>> attendanceHistories = new HashMap<>();
        getStrings().stream().skip(HEADER_ROW)
                .forEach(row -> {
                    String crew = parseCrew(row);
                    LocalDateTime localDateTime = parseLocalDateTime(row);
                    attendanceHistories.computeIfAbsent(crew, k -> new ArrayList<>()).add(localDateTime);
                });
        return attendanceHistories;
    }

    private List<String> getStrings() {
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            if (inputStream == null) {
                throw new IllegalStateException("[ERROR] CSV 파일을 찾을 수 없습니다.");
            }
            return reader.lines().toList();
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] CSV 파일을 읽어올 수 없습니다.");
        }
    }

    private String parseCrew(String row) {
        return row.split(",")[CREW_INDEX];
    }

    private LocalDateTime parseLocalDateTime(String row) {
        String dateTimeRecord = row.split(CREW_DATETIME_SEPARATOR)[DATETIME_INDEX];
        return LocalDateTime.parse(dateTimeRecord, CSV_DATETIME_PATTERN);
    }
}
