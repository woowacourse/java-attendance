package attendance.domain;

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

    public static AttendanceBook load(final List<String> data) {
        AttendanceBook attendanceBook = new AttendanceBook();
        for (String line : data) {
            List<String> contents = Arrays.asList(line.split(","));
            LocalDateTime attendance = LocalDateTime.parse(contents.getLast().replace(" ", "T"));

            attendanceBook.addAttendance(contents.getFirst(), attendance);
        }
        return attendanceBook;
    }
}
