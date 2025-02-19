package attendance.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceResult {
    private LocalDateTime attendanceTime;
    private String attendanceType;

    public AttendanceResult(LocalDateTime attendanceTime, String attendanceType) {
        this.attendanceTime = attendanceTime;
        this.attendanceType = attendanceType;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getAttendanceType() {
        return attendanceType;
    }

    public void modify(AttendanceResult attendanceResult) {
        this.attendanceTime = attendanceResult.getAttendanceTime();
        this.attendanceType = attendanceResult.getAttendanceType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceResult that = (AttendanceResult) o;
        return Objects.equals(attendanceTime.toLocalDate(), that.attendanceTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime.toLocalDate());
    }
}
