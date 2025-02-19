package attendance.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceDetail {
    private LocalDateTime localDateTime;
    private Attendance attendance;

    public AttendanceDetail(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.attendance = Attendance.from(localDateTime);
    }

    public void modify(LocalTime localTime) {
        localDateTime = LocalDateTime.of(
                localDateTime.toLocalDate(),
                localTime
        );
        this.attendance = Attendance.from(localDateTime);
    }

    public Attendance getAttandence() {
        return attendance;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public AttendanceDetail clone() {
        return new AttendanceDetail(this.localDateTime);
    }
}
