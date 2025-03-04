package attendance.io.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceInfoLinesReader {

    private static final Path ATTENDANCE_PATH = Path.of("src/main/resources/attendances.csv");
    private static final String LINE_DELIMITER = ",";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public List<AttendanceInfo> readAttendanceInfos() {
        List<String> lines = readAttendanceRecordsWithoutHeader();

        List<AttendanceInfo> attendanceInfos = new ArrayList<>();
        for (String line : lines) {
            String[] values = line.split(LINE_DELIMITER);
            String nickname = values[0];
            LocalDateTime attendanceTime = LocalDateTime.parse(values[1], DATE_TIME_FORMAT);
            attendanceInfos.add(new AttendanceInfo(nickname, attendanceTime));
        }
        return attendanceInfos;
    }

    private List<String> readAttendanceRecordsWithoutHeader() {
        List<String> lines = readAttendanceRecords();
        lines.removeFirst();
        return lines;
    }

    private List<String> readAttendanceRecords() {
        try {
            return Files.readAllLines(ATTENDANCE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
