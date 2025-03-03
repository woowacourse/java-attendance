package domain.dateTime;

import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceTimePolicy {
    MONDAY(LocalTime.of(13, 0)),
    TUESDAY(LocalTime.of(10, 0)),
    WEDNESDAY(LocalTime.of(10, 0)),
    THURSDAY(LocalTime.of(10, 0)),
    FRIDAY(LocalTime.of(10, 0));

    public static final LocalTime START_TIME = LocalTime.of(8, 0);
    public static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final LocalTime attendanceTime;

    AttendanceTimePolicy(final LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public static AttendanceTimePolicy findByAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        return Arrays.stream(AttendanceTimePolicy.values())
                .filter(attendanceTimePolicy -> attendanceDateTime.isEqualToDayOfWeek(attendanceTimePolicy.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("주말은 운영하지 않습니다."));
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }
}
