package attendance.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceHistory {
    private LocalDateTime attendanceTime;
    private AttendanceType attendanceType;

    public AttendanceHistory(LocalDateTime attendanceTime, AttendanceType attendanceType) {
        this.attendanceTime = attendanceTime;
        this.attendanceType = attendanceType;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public void modify(AttendanceHistory attendanceHistory) {
        this.attendanceTime = attendanceHistory.getAttendanceTime();
        this.attendanceType = attendanceHistory.getAttendanceType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceHistory that = (AttendanceHistory) o;
        return Objects.equals(attendanceTime.toLocalDate(), that.attendanceTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime.toLocalDate());
    }
}
