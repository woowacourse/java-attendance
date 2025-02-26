package domain;

public class AttendanceRecord implements Comparable<AttendanceRecord> {

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceRecord(final AttendanceDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = determinAttendanceStatus(attendanceDateTime);
    }

    private AttendanceStatus determinAttendanceStatus(final AttendanceDateTime attendanceDateTime) {
        final AttendanceTimePolicy attendanceTimePolicy = AttendanceTimePolicy.findByAttendanceDateTime(
                attendanceDateTime);
        return AttendanceStatus.findByAttendanceDateTime(attendanceDateTime, attendanceTimePolicy);
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    @Override
    public int compareTo(final AttendanceRecord o) {
        return attendanceDateTime.getDateTime().compareTo(o.attendanceDateTime.getDateTime());
    }
}
