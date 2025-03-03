package attendance.domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceFileReader {
    private static final String CANNOT_FIND_FILE = "[ERROR] 파일을 찾을 수 없습니다: ";
    private static final String CANNOT_READ_FILE = "[ERROR] 파일을 읽는 과정에서 예상치 못한 오류가 발생했습니다: ";

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String REGEX = ",";

    private final String fileName;

    public AttendanceFileReader(String fileName) {
        this.fileName = fileName;
    }

    public FileRecords load() throws FileNotFoundException {
        InputStream inputStream = getInputStream();
        try (
            var inputStreamReader = new InputStreamReader(inputStream);
            var bufferedReader = new BufferedReader(inputStreamReader)) {
            var lines = readLines(bufferedReader);
            inputStream.close();
            return convertToFileRecords(lines);
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_READ_FILE + fileName);
        }
    }

    private InputStream getInputStream() throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new FileNotFoundException(CANNOT_FIND_FILE + fileName);
        }
        return inputStream;
    }

    private FileRecords convertToFileRecords(List<String> lines) {
        Map<String, LocalDateTime> records = new HashMap<>();
        for (String line : lines) {
            var parts = line.split(REGEX);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(parts[1], formatter);
            records.put(parts[0], dateTime);
        }
        return new FileRecords(records);
    }

    private List<String> readLines(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .skip(1)
            .toList();
    }
}
