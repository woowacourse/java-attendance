package domain;

import java.time.LocalDateTime;

public class AttendanceRecord {

    private final LocalDateTime attendanceDateTime;

    public AttendanceRecord(final LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public LocalDateTime getDateTime() {
        return this.attendanceDateTime;
    }
}
