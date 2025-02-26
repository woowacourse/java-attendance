package policy;

import policy.date.AttendanceDatePolicy;
import policy.time.rule.AttendanceStateRule;
import policy.time.AttendanceTimePolicy;

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

    public AttendanceStateRule decideState(LocalDate date, LocalTime localTime) {
        LocalTime AttendStartTime = attendanceTimePolicy.getAttendStartTime(
                attendanceDatePolicy.isSpecialDay(date));

        long lateMinutes = Duration.between(AttendStartTime, localTime)
                .toMinutes();

        return attendanceTimePolicy.decisionAttendanceState(lateMinutes);
    }
}
