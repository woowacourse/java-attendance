package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private final String crewName;
    private final LocalDateTime attendanceTime;

    public Attendance(String crewName, LocalDateTime attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }
}
