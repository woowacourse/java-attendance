package model;

import java.time.LocalTime;
import java.util.Objects;

public class AttendanceTime {
    private final LocalTime attendanceTime;
    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    public AttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public boolean isAfter(AttendanceTime wantToCompareAttendanceTime) {
        return attendanceTime.isAfter(wantToCompareAttendanceTime.toLocalTime());
    }

    public boolean isNotOpeningTime() {
        return attendanceTime.isBefore(OPEN_TIME) || attendanceTime.isAfter(END_TIME);
    }

    public boolean isZeroTime() {
        return this.toLocalTime().equals(LocalTime.of(0, 0));
    }

    public LocalTime toLocalTime() {
        return this.attendanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceTime that = (AttendanceTime) o;
        return Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime);
    }
}
