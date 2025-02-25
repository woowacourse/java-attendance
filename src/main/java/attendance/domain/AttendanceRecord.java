package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceRecord {
    private LocalDateTime attendanceDateTime;

    public AttendanceRecord(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        //AttendanceStatus.from()
        return AttendanceStatus.PRESENT;
    }
}
