package domain;

import java.time.LocalDate;
import java.util.Objects;

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

    public boolean hasAttendanceDate(final LocalDate attendanceDate) {
        return attendanceDateTime.isSameDate(attendanceDate);
    }

    public boolean hasAttendanceDateTime(final AttendanceDateTime dateTime) {
        return attendanceDateTime.equals(dateTime);
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(attendanceDateTime, that.attendanceDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDateTime);
    }

    @Override
    public int compareTo(final AttendanceRecord o) {
        return attendanceDateTime.compareTo(o.attendanceDateTime);
    }
}
