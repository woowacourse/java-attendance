package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    public static final LocalTime ABSENT_TIME = LocalTime.of(23, 0);

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public Attendance(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance makeAbsence(final LocalDate absentDate) {
        return new Attendance(new AttendanceDate(absentDate), new AttendanceTime(ABSENT_TIME));
    }

    public Attendance changeAttendanceTime(final LocalTime changeTime) {
        return new Attendance(this.attendanceDate, new AttendanceTime(changeTime));
    }

    public boolean isSameDate(final LocalDate findDate) {
        return attendanceDate.isSameDate(findDate);
    }

    public AttendanceStatus calculateStatus() {
        return AttendanceStatus.findByAttendanceDateAndTime(attendanceDate, attendanceTime);
    }

    public LocalDateTime getAttendanceDateTime() {
        return LocalDateTime.of(attendanceDate.getLocalDate(), attendanceTime.getAttendanceTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance that)) {
            return false;
        }
        return getAttendanceDateTime().equals(that.getAttendanceDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttendanceDateTime());
    }

}
