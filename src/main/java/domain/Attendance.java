package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Attendance {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    LocalDateTime localDateTime;
    AttendanceStatus attendanceStatus;

    public static Attendance of(final String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        try {
            LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
            return new Attendance(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 형식이 아닙니다.");
        }
    }

    public Attendance(final LocalDateTime localDateTime) {
        Week day = Week.findByAttendanceTime(localDateTime);
        this.localDateTime = localDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, localDateTime.toLocalTime());
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
