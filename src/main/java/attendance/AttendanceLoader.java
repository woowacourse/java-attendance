package attendance;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AttendanceLoader {
    public static AttendanceBook load(final String data) {
        List<String> contents = Arrays.asList(data.split(","));
        LocalDateTime attendance = LocalDateTime.parse(contents.getLast().replace(" ", "T"));

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.addAttendance(contents.getFirst(), attendance);

        return attendanceBook;
    }
}
