package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import util.Constants;

public class Attendance implements Comparable<Attendance> {

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(final AttendanceDateTime attendanceDateTime) {
        final Week day = Week.findByAttendanceTime(attendanceDateTime.getLocalDateTime());
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, attendanceDateTime.toLocalTime());
    }

    public static Attendance of(final String inputTime) {
        return new Attendance(AttendanceDateTime.of(inputTime));
    }

    public static Attendance generateAbsentAttendance(final Integer attendanceDate) {
        final LocalDate localDate = LocalDate.of(Constants.FIXED_YEAR, Constants.FIXED_MONTH, attendanceDate);
        final LocalDateTime dateTime = LocalDateTime.of(localDate, Constants.ABSENCE_TIME);
        return new Attendance(AttendanceDateTime.of(dateTime));
    }

    public AttendanceSummary getSummary() {
        return new AttendanceSummary(attendanceDateTime, attendanceStatus);
    }

    public int getDayOfMonth() {
        return attendanceDateTime.getDayOfMonth();
    }

    public LocalDateTime getLocalDateTime() {
        return attendanceDateTime.getLocalDateTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    @Override
    public int compareTo(final Attendance object) {
        return this.getDayOfMonth() - object.getDayOfMonth();
    }
}
