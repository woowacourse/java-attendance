import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
