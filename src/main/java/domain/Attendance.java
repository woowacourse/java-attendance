package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.Constants;

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

    public static Attendance generateAbsentAttendance(Integer attendanceDate) {
        final LocalDate localDate = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, attendanceDate);
        final LocalDateTime dateTime = LocalDateTime.of(localDate, Constants.ABSENCE_TIME);
        return new Attendance(AttendanceDateTime.of(dateTime));
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
