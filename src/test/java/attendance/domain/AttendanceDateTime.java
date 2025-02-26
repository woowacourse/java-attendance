package attendance.domain;

import java.time.LocalDateTime;

public class AttendanceDateTime {

    private final LocalDateTime dateTime;

    public AttendanceDateTime(LocalDateTime attendanceDateTime) {
        this.dateTime = attendanceDateTime;
    }
}
