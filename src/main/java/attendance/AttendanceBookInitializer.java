package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crews;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceBookInitializer {

    private static final Path ATTENDANCE_PATH = Path.of("src/main/resources/attendances.csv");
    private static final String LINE_DELIMITER = ",";
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AttendanceBook Initialize() {
        List<String> lines = readAttendanceRecords();
        lines.removeFirst();

        Set<String> nicknames = new HashSet<>();
        List<Attendance> attendances = new ArrayList<>();
        for (String line : lines) {
            String[] values = line.split(LINE_DELIMITER);
            String nickname = values[0];
            LocalDateTime attendanceTime = LocalDateTime.parse(values[1], DATE_TIME_FORMAT);

            nicknames.add(nickname);
            attendances.add(new Attendance(nickname, attendanceTime));
        }

        return new AttendanceBook(new Crews(nicknames), attendances);
    }

    private List<String> readAttendanceRecords() {
        try {
            return Files.readAllLines(ATTENDANCE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
