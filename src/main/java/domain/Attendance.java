package domain;

import domain.policy.AttendanceStateRule;
import domain.policy.attend.AttendancePolicy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    private Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        return new Attendance(attendanceDate, attendanceTime);
    }

    public AttendanceStateRule decideAttendanceState(AttendancePolicy attendancePolicy) {
        return attendancePolicy.decideAttendanceState(this);
    }

    public LocalDate getLocalDate() {
        return attendanceDate.toLocalDate();
    }

    public LocalTime getLocalTime() {
        return attendanceTime.toLocalTime();
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(attendanceDate, that.attendanceDate) && Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceDate, attendanceTime);
    }
}
