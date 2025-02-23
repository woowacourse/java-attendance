package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceHistory {
    private LocalDateTime attendanceDateTime;
    private AttendanceType attendanceType;

    public AttendanceHistory(LocalDateTime attendanceDateTime, AttendanceType attendanceType) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceType = attendanceType;
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    /**
     * TODO
     * 내부에서 boolean을 반환할 수 있겠다
     */
    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public void modify(LocalTime modifyTime, AttendanceType attendanceType) {
        this.attendanceDateTime = LocalDateTime.of(attendanceDateTime.toLocalDate(), modifyTime);
        this.attendanceType = attendanceType;
    }

    public boolean isAttendanceDateEquals(LocalDate date) {
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        return attendanceDate.equals(date);
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
        return Objects.equals(attendanceDateTime.toLocalDate(), that.attendanceDateTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDateTime.toLocalDate());
    }
}
