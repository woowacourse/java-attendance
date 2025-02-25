package attendance;

import java.time.LocalDate;
import java.util.Objects;

public class Attendance {
    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;

    public Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public boolean isEqualToDate(LocalDate date) {
        return this.attendanceDate.isEqualToDate(date);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime);
    }
}
