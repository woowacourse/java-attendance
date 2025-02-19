package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<AttendanceRecord> loadAttendanceRecords() {
        File file = new File("src/main/resources/attendances.csv");
        BufferedReader br = null;
        String line;

        List<AttendanceRecord> result = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                result.add(parseAttendanceHistory(line));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static AttendanceRecord parseAttendanceHistory(String attendanceData) {
        List<String> parsed = Arrays.stream(attendanceData.split(",", -1)).toList();
        LocalDateTime dateTime = LocalDateTime.parse(parsed.get(1), FORMATTER);

        return new AttendanceRecord(
            parsed.get(0),
            dateTime.toLocalDate(),
            dateTime.toLocalTime()
        );
    }
}
