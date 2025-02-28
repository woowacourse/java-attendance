package model;

import java.time.LocalDateTime;

public class Attendance implements Comparable<Attendance>{

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    private Attendance(final AttendanceDateTime attendanceDateTime, final AttendanceStatus attendanceStatus) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = attendanceStatus;
    }

    public static Attendance of(final AttendanceDateTime attendanceDateTime) {
        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime);
        return new Attendance(attendanceDateTime, attendanceStatus);
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    @Override
    public int compareTo(final Attendance attendance) {
        final LocalDateTime srcDateTIme = this.attendanceDateTime.getDateTime();
        final LocalDateTime descDateTIme = attendance.attendanceDateTime.getDateTime();
        if (srcDateTIme.getMonth().getValue() == descDateTIme.getMonth().getValue()) {
            return srcDateTIme.getDayOfMonth() - descDateTIme.getDayOfMonth();
        }
        return srcDateTIme.getMonth().getValue() - descDateTIme.getMonth().getValue();
    }
}
