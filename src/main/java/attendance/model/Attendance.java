package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private final LocalDateTime dateTime;
    private AttendanceType type;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Attendance(LocalDateTime dateTime, AttendanceType type) {
        this.dateTime = dateTime;
        this.type = type;
    }

    public void calculateAttendanceType() {
        this.type = AttendanceType.of(dateTime);
    }

    public AttendanceType getType() {
        return type;
    }

    public boolean isSameDate(LocalDate date) {
        return dateTime.toLocalDate().equals(date);
    }
}
