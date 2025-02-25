package constants;

import java.time.LocalTime;

public enum AttendanceCriteria {
    OPERATING_START(LocalTime.of(8, 0)),
    OPERATING_END(LocalTime.of(23, 0)),

    MONDAY_ATTEND(LocalTime.of(10, 5)),
    MONDAY_LATE(LocalTime.of(10, 30)),

    EXCEPT_MONDAY_ATTEND(LocalTime.of(13, 5)),
    EXCEPT_MONDAY_LATE(LocalTime.of(13, 30));

    private final LocalTime time;

    AttendanceCriteria(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }
}