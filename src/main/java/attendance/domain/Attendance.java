package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;

    public Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance from(LocalDateTime dateTime) {
        return new Attendance(AttendanceDate.from(LocalDate.from(dateTime)),
                AttendanceTime.from(LocalTime.from(dateTime)));
    }

    public boolean isEqualToDate(LocalDate date) {
        return this.attendanceDate.isEqualToDate(date);
    }

    public void updateTime(LocalTime time) {
        this.attendanceTime = AttendanceTime.from(time);
    }

    public AttendanceStatus checkAttendanceStatus() {
        return attendanceTime.checkAttendanceStatus(attendanceDate.isMonday());
    }

    public boolean isSameDate(Attendance attendance) {
        return this.attendanceDate.isEqualToDate(attendance.attendanceDate.date());
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
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
