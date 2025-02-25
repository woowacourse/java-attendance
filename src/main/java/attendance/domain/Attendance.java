package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {

    private final LocalDateTime attendanceDateTime;
    private final String attendanceStatus;

    public Attendance(LocalDateTime attendacneTime) {
        this.attendanceDateTime = attendacneTime;
        this.attendanceStatus = checkAttendanceStatus();
    }

    private String checkAttendanceStatus() {
        return "출석";
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
