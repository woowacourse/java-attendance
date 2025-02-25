package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceHistory {

    private final LocalDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceHistory(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = attendanceStatus;
    }
}
