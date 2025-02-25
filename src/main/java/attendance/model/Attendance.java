package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private final Crew crew;
    private final LocalDateTime dateTime;
    private AttendanceType type;

    public Attendance(Crew crew, LocalDateTime dateTime) {
        this.crew = crew;
        this.dateTime = dateTime;
    }

    public Attendance(Crew crew, LocalDateTime dateTime, AttendanceType type) {
        this.crew = crew;
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
