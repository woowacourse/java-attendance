package attendance.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceDateTime {
    private final AttendanceDate attendanceDate;
    private LocalTime attendanceTime;

    public AttendanceDateTime(AttendanceDate attendanceDate, LocalTime attendanceTime) {
        validateDurationTime(attendanceDate, attendanceTime);
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public void modifyAttendanceTime(LocalTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public boolean equalsDate(AttendanceDate attendanceDate) {
        return this.attendanceDate.equals(attendanceDate);
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
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

    private void validateDurationTime(AttendanceDate attendanceDate, LocalTime attendanceTime) {
        if (!CampusOpenTime.isDurationTime(attendanceDate.date(), attendanceTime)) {
            throw new IllegalArgumentException(
                    attendanceTime.format(DateTimeFormatter.ofPattern("HH시 mm분은 등교시간이 아닙니다.")
                    ));
        }
    }
}
