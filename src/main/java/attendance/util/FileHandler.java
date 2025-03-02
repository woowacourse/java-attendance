package attendance.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileHandler {
    private static final String FILE_NOT_FOUND = "[ERROR] 존재하지 않는 파일입니다.";
    private static final String FILE_NO_CONTENT = "[ERROR] 파일 내용이 존재하지 않습니다.";

    private static final String FILE_NAME = "attendances.csv";
    private static final String DELIMITER_COMMA = ",";
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";

    private final Map<String, List<LocalDateTime>> data = new HashMap<>();
    private BufferedReader reader;

    public Map<String, List<LocalDateTime>> provideDataFromFile() {
        readFile();
        return data;
    }

    private void readFile() {
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(FILE_NAME)) {
            reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream, FILE_NOT_FOUND)));
            parseData();
        } catch (IOException e) {
            throw new IllegalStateException(FILE_NOT_FOUND);
        }
    }

    private void parseData() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            throw new IllegalStateException(FILE_NO_CONTENT);
        }
        while ((line = reader.readLine()) != null) {
            List<String> row = Arrays.asList(line.split(DELIMITER_COMMA));
            String crewName = row.getFirst();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
            LocalDateTime dateTime = LocalDateTime.parse(row.getLast(), formatter);
            data.computeIfAbsent(crewName, crew -> new ArrayList<>()).add(dateTime);
        }
    }
}
