package attendance.model;

import java.time.LocalDateTime;

public class Attendance {
    private final Crew crew;
    private LocalDateTime dateTime;
    private AttendanceType type;

    public Attendance(Crew crew, LocalDateTime dateTime) {
        this.crew = crew;
        this.dateTime = dateTime;
    }

    public void calculateAttendanceType() {
        this.type = AttendanceType.of(dateTime);
    }

    public AttendanceType getType() {
        return type;
    }

}
