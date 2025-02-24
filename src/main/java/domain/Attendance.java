package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    public Attendance(final AttendanceDateTime attendanceDateTime) {
        Week day = Week.findByAttendanceTime(attendanceDateTime);
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = AttendanceStatus.findByAttendanceTime(day, attendanceDateTime.getTime());
    }

    public boolean equals(final int findDayOfMonth) {
        return attendanceDateTime.getDayOfMonth() == findDayOfMonth;
    }

    public int getDateOfMonth() {
        return attendanceDateTime.getDayOfMonth();
    }

    public LocalDateTime getLocalDateTime() {
        return attendanceDateTime.getDateTime();
    }

    public LocalTime getTime() {
        return attendanceDateTime.getTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public boolean equalsDate(LocalDate date) {
        return attendanceDateTime.getDate().equals(date);
    }
}
