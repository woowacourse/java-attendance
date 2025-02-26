package attendance.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public Attendance(final LocalDateTime attendanceDateTime) {
        this.attendanceDate = new AttendanceDate(attendanceDateTime.toLocalDate());
        this.attendanceTime = new AttendanceTime(attendanceDateTime.toLocalTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance that)) {
            return false;
        }
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }

}
