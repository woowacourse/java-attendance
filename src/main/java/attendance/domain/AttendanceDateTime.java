package attendance.domain;

import java.util.Objects;

public class AttendanceDateTime {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public AttendanceDateTime(
        final AttendanceDate attendanceDate,
        final AttendanceTime attendanceTime
    ) {
        validateNotNull(attendanceDate, attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    private void validateNotNull(
        final AttendanceDate attendanceDate,
        final AttendanceTime attendanceTime
    ) {
        if (attendanceDate == null || attendanceTime == null) {
            throw new IllegalArgumentException(
                "출석 일시는 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
        }
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(attendanceDate, that.attendanceDate)
            && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }
}
