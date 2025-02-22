package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AttendTime {

    private final LocalDateTime attendTime;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AttendTime(String attendTime) {
        this.attendTime = LocalDateTime.parse(attendTime, FORMATTER);
    }

    public AttendanceType checkTime() {
        return AttendanceType.checkTime(attendTime);
    }

    public LocalDateTime getAttendTime() {
        return attendTime;
    }
}
