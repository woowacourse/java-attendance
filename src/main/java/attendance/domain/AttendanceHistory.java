package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceHistory {

    private final AttendanceTime attendanceTime;
    private final AttendanceType attendanceType;

    private AttendanceHistory(LocalDateTime inputAttendanceTime) {
        this.attendanceTime = AttendanceTime.from(inputAttendanceTime);
        this.attendanceType = attendanceTime.calculateAttendanceType();
    }

    public static AttendanceHistory from(LocalDateTime inputAttendanceTime) {
        return new AttendanceHistory(inputAttendanceTime);
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }

    public boolean findAttendanceTimeByDate(LocalDate findDate) {
        return attendanceTime.isSameDate(findDate);
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public LocalDate getAttendanceDate() {
        return attendanceTime.getLocalDate();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceHistory that = (AttendanceHistory) o;
        return Objects.equals(attendanceTime, that.attendanceTime)
            && attendanceType == that.attendanceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime, attendanceType);
    }
}
