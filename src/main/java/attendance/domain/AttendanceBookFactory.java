package attendance.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attendance.utility.DateTimeFormatterWrapper;

public class AttendanceBookFactory {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String REGEX = ",";

    private final SystemDateTime systemDateTime;

    public AttendanceBookFactory(SystemDateTime systemDateTime) {
        this.systemDateTime = systemDateTime;
    }

    public AttendanceBook from(List<String> lines) {
        Map<String, Attendances> attendanceBook = new HashMap<>();
        for (String line : lines) {
            addAttendance(line, attendanceBook);
        }

        return new AttendanceBook(attendanceBook);
    }

    private void addAttendance(String line, Map<String, Attendances> attendanceBook) {
        var lines = line.split(REGEX);
        var nickname = lines[0];

        Attendances attendances = attendanceBook.computeIfAbsent(nickname, k -> new Attendances());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatterWrapper.getFormatter(DATETIME_FORMAT);
        var dateTime = LocalDateTime.parse(lines[1], dateTimeFormatter);
        var attendance = Attendance.of(dateTime, systemDateTime);

        attendances.add(attendance);
    }
}
