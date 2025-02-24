package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private final Crew crew;
    private LocalDateTime dateTime;
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

    public boolean isCrewAttendance(Crew crew) {
        return crew.equals(crew);
    }

    public boolean isSameDateTime(LocalDateTime dateTime) {
        return this.dateTime.equals(dateTime);
    }

    public boolean isSameDate(LocalDate localDate) {
        return this.dateTime.toLocalDate().equals(localDate);
    }

    public void modifyTime(LocalDateTime modifiedDateTime) {
        this.dateTime = modifiedDateTime;
        this.type = AttendanceType.of(modifiedDateTime);
    }
}
