package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceDateTime {
    private final LocalDate attendanceDate;
    private LocalTime attendanceTime;

    public AttendanceDateTime(LocalDate attendanceDate, LocalTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public void modifyAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public boolean equalsDate(LocalDate localDate) {
        return this.attendanceDate.equals(localDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceDateTime that)) {
            return false;
        }
        return Objects.equals(attendanceDate, that.attendanceDate) &&
                Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }
}
