package domain;

import java.time.LocalDateTime;

public class Attendance {

    AttendanceDateTime attendanceDateTime;
    AttendanceStatus attendanceStatus;

    public Attendance(final AttendanceDateTime attendanceDateTime) {
        Week day = Week.findByAttendanceTime(attendanceDateTime.getLocalDateTime());
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, attendanceDateTime.toLocalTime());
    }

    public static Attendance of(final String inputTime) {
        return new Attendance(AttendanceDateTime.of(inputTime));
    }

    public int getDate() {
        return attendanceDateTime.getDayOfMonth();
    }

    public LocalDateTime getLocalDateTime() {
        return attendanceDateTime.getLocalDateTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }
}
