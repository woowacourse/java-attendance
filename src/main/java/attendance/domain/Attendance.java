package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private final Crew crew;
    private final LocalDateTime presentTime;
    private final AttendanceType attendanceType;

    public Attendance(Crew crew, LocalDateTime presentTime, AttendanceType attendanceType) {
        this.crew = crew;
        this.presentTime = presentTime;
        this.attendanceType = attendanceType;
    }
}
