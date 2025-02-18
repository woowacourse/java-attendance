import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileParser {

    public static AttendanceRecord parseAttendanceHistory(String attendanceData) {
        List<String> parsed = Arrays.stream(attendanceData.split(",", -1)).toList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        return new AttendanceRecord(
            parsed.get(0),
            LocalDate.parse(parsed.get(1), formatter),
            LocalTime.parse(parsed.get(2), formatter2));
    }
}
