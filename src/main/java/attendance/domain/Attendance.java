package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private final AttendanceDate attendanceDate;
    private AttendanceTime attendanceTime;

    private Attendance(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(final AttendanceDate attendanceDate, final AttendanceTime attendanceTime) {
        return new Attendance(attendanceDate, attendanceTime);
    }

    public static Attendance from(final LocalDateTime dateTime) {
        return new Attendance(AttendanceDate.from(LocalDate.from(dateTime)),
                AttendanceTime.from(LocalTime.from(dateTime)));
    }

    public void updateTime(final LocalTime time) {
        this.attendanceTime = AttendanceTime.from(time);
    }

    public boolean isEqualToDate(final LocalDate date) {
        return this.attendanceDate.isEqualToDate(date);
    }

    public boolean isEqualToDateByAttendance(final Attendance attendance) {
        return this.attendanceDate.isEqualToDate(attendance.attendanceDate.date());
    }

    public AttendanceStatus checkAttendanceStatus() {
        return attendanceTime.checkAttendanceStatus(attendanceDate.isMonday());
    }

    public LocalDateTime getAttendanceDateTime() {
        return LocalDateTime.of(this.attendanceDate.date(), this.attendanceTime.time());
    }
}
