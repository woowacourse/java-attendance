package domain;

import domain.policy.AttendancePolicy;
import domain.policy.time.rule.AttendanceStateRule;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {

    private final AttendanceDate attendanceDate;
    private final AttendanceTime attendanceTime;

    public Attendance(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        this.attendanceDate = attendanceDate;
        this.attendanceTime = attendanceTime;
    }

    public static Attendance of(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        return new Attendance(attendanceDate, attendanceTime);
    }

    public AttendanceStateRule decideAttendanceState(AttendancePolicy attendancePolicy) {
        return attendancePolicy.decideAttendanceState(this);
    }

    public LocalDate toLocalDate() {
        return attendanceDate.toLocalDate();
    }

    public LocalTime toLocalTime() {
        return attendanceTime.toLocalTime();
    }

    public AttendanceDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceTime getAttendanceTime() {
        return attendanceTime;
    }
}
