package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceBookInitializer {

    private static final Path ATTENDANCE_PATH = Path.of("src/main/resources/attendances.csv");
    private static final String LINE_DELIMITER = ",";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AttendanceBook Initialize() {
        List<String> lines = readAttendanceRecords();
        lines.removeFirst();

        List<Attendance> attendances = lines.stream()
                .map(line -> line.split(LINE_DELIMITER))
                .map(line -> new Attendance(line[0], LocalDateTime.parse(line[1], DATE_TIME_FORMAT)))
                .collect(Collectors.toList());
        return new AttendanceBook(attendances);
    }

    private List<String> readAttendanceRecords() {
        try {
            return Files.readAllLines(ATTENDANCE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
