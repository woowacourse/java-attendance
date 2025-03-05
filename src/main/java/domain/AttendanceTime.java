package domain;

import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {
    private final LocalTime attendanceTime;
    private static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    public AttendanceTime(LocalTime attendanceTime) {
        validateIsOperatingHours(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    public LocalTime getTime() {
        return attendanceTime;
    }

    private void validateIsOperatingHours(LocalTime attendanceTime) {
        if (isNotOperatingHours(attendanceTime)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private boolean isNotOperatingHours(LocalTime time) {
        return time.isBefore(CAMPUS_START_TIME) || time.isAfter(CAMPUS_END_TIME);
    }

    public static AttendanceTime of(int hour, int minute) {
        return new AttendanceTime(LocalTime.of(hour, minute));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceTime that = (AttendanceTime) o;
        return attendanceTime.equals(that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime);
    }
}
