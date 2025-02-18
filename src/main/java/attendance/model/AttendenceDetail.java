package attendance.model;

import java.time.LocalDateTime;

public class AttendenceDetail {
    private final LocalDateTime localDateTime;
    private final Attendence attendence;

    public AttendenceDetail(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        this.attendence = Attendence.from(localDateTime);
    }

    public Attendence getAttendence() {
        return attendence;
    }
}
