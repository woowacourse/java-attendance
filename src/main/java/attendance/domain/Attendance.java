package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance implements Comparable<Attendance> {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public Attendance(final LocalDateTime attendanceDateTime) {
        this.attendanceDate = new AttendanceDate(attendanceDateTime.toLocalDate());
        this.attendanceTime = new AttendanceTime(attendanceDateTime.toLocalTime());
    }

    public boolean isSameDate(final LocalDate localDate) {
        return attendanceDate.isSameDate(localDate);
    }

    public boolean isSameDate(final Attendance otherAttendance) {
        return this.attendanceDate.equals(otherAttendance.attendanceDate);
    }

    public Attendance changeTime(final LocalDateTime modificationDateTime) {
        return new Attendance(modificationDateTime);
    }

    public boolean isBeforeOrEqualDate(final LocalDate localDate) {
        return attendanceDate.isBeforeOrEqualDate(localDate);
    }

    public boolean isMonday() {
        return attendanceDate.isMonday();
    }

    public boolean isAttendanceComplete() {
        return AttendanceStatus.isAttendance(this, attendanceTime);
    }

    public boolean isLate() {
        return AttendanceStatus.isLate(this, attendanceTime);
    }

    public boolean isAbsent() {
        return AttendanceStatus.isAbsent(this, attendanceTime);
    }

    public LocalDate getAttendanceLocalDate() {
        return attendanceDate.getAttendanceDate();
    }

    public LocalTime getAttendanceLocalTime() {
        return attendanceTime.getAttendanceTime();
    }

    @Override
    public int compareTo(final Attendance o) {
        return this.attendanceDate.compareTo(o.attendanceDate);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance that)) {
            return false;
        }
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime,
                that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }

}
