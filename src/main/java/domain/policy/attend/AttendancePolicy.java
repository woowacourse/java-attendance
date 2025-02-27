package domain.policy.attend;

import domain.Attendance;
import domain.policy.attend.date.AttendanceDatePolicy;
import domain.policy.attend.time.AttendanceTimePolicy;
import domain.policy.AttendanceStateRule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendancePolicy {

    private final AttendanceDatePolicy attendanceDatePolicy;
    private final AttendanceTimePolicy attendanceTimePolicy;

    public AttendancePolicy(AttendanceDatePolicy attendanceDatePolicy,
                            AttendanceTimePolicy attendanceTimePolicy) {
        this.attendanceDatePolicy = attendanceDatePolicy;
        this.attendanceTimePolicy = attendanceTimePolicy;
    }

    public boolean canAttendTime(LocalTime time) {
        return attendanceTimePolicy.canAttendTime(time);
    }

    public boolean canAttendDate(LocalDate date) {
        return !attendanceDatePolicy.isHoliday(date) && !attendanceDatePolicy.isWeekend(date);
    }

    public AttendanceStateRule decideAttendanceState(Attendance attendance) {
        LocalTime AttendStartTime = attendanceTimePolicy.getAttendStartTime(
                attendanceDatePolicy.isSpecialDay(attendance.toLocalDate()));

        long lateMinutes = Duration.between(AttendStartTime, attendance.toLocalTime())
                .toMinutes();

        return attendanceTimePolicy.decisionAttendanceState(lateMinutes);
    }
}
