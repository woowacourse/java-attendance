package attendance.domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceReader {
    private static final String CANT_FIND_FILE = "[ERROR] 파일을 찾을 수 없습니다: ";
    private static final String CANT_READ_FILE = "[ERROR] 파일을 읽는 과정에서 예상치 못한 오류가 발생했습니다: ";
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String REGEX = ",";

    private final String fileName;
    private final SystemDateTime systemDateTime;

    public AttendanceReader(String fileName, SystemDateTime systemDateTime) {
        this.fileName = fileName;
        this.systemDateTime = systemDateTime;
    }

    public AttendanceBook load() throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new FileNotFoundException(CANT_FIND_FILE + fileName);
        }
        try (var inputStreamReader = new InputStreamReader(inputStream);
             var bufferedReader = new BufferedReader(inputStreamReader)) {
            return generateAttendanceBook(bufferedReader);
        } catch (IOException e) {
            throw new RuntimeException(CANT_READ_FILE + fileName);
        }
    }

    private AttendanceBook generateAttendanceBook(BufferedReader bufferedReader) {
        var lines = readLines(bufferedReader);
        var attendanceBook = new AttendanceBook(systemDateTime);
        for (String line : lines) {
            var parts = line.split(REGEX);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(parts[1], formatter);
            attendanceBook.put(parts[0], dateTime);
        }
        return attendanceBook;
    }

    private List<String> readLines(BufferedReader bufferedReader) {
        return bufferedReader.lines()
            .skip(1)
            .toList();
    }
}
