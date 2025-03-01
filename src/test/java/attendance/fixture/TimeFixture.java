package attendance.fixture;

import java.time.LocalTime;

public class TimeFixture {

    public static final LocalTime MONDAY_ATTENDANCE_TIME = LocalTime.of(13, 0);
    public static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    public static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);
    public static final LocalTime NOT_MONDAY_ATTENDANCE_TIME = LocalTime.of(10, 0);
    public static final LocalTime NOT_MONDAY_LATE_TIME = LocalTime.of(10, 5);
    public static final LocalTime NOT_MONDAY_ABSENCE_TIME = LocalTime.of(10, 30);
}
