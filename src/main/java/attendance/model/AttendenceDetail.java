package attendance.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendenceDetail {
    private LocalDateTime localDateTime;
    private final Attendence attendence;

    public AttendenceDetail(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.attendence = Attendence.from(localDateTime);
    }

    public void modify(LocalTime localTime) {
        localDateTime = LocalDateTime.of(
                localDateTime.toLocalDate(),
                localTime
        );
    }

    public Attendence getAttendence() {
        return attendence;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
