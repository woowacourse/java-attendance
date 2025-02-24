package domain;

import domain.rule.AttendanceStateRule;

import java.time.LocalDate;
import java.time.LocalTime;

public record Attendance(
        AttendanceDate attendanceDate,
        AttendanceTime attendanceTime
) {
    public static Attendance from(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        return new Attendance(attendanceDate, attendanceTime);
    }

    public AttendanceStateRule decisionAttendanceState() {
        return attendanceTime.checkAttendanceState(attendanceDate().isSpecialDay());
    }

    public LocalDate toLocalDate() {
        return attendanceDate.date();
    }

    public LocalTime toLocalTime() {
        return attendanceTime.time();
    }
}
