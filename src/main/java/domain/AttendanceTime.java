package domain;

import domain.rule.AttendanceStateRule;
import domain.rule.AttendanceTimeRule;

import java.time.Duration;
import java.time.LocalTime;

public record AttendanceTime(LocalTime time) {

    public AttendanceTime {
        validate(time);
    }

    public static AttendanceTime from(LocalTime time) {
        return new AttendanceTime(time);
    }

    public AttendanceStateRule checkAttendanceState(boolean isSpecialDay) {
        LocalTime AttendLimitTime = AttendanceTimeRule.getAttendLimitTime(isSpecialDay);
        long timeDifference = Duration.between(AttendLimitTime, time).toMinutes();

        if (timeDifference > AttendanceStateRule.ABSENT.getLimit()) {
            return AttendanceStateRule.ABSENT;
        }

        if (timeDifference > AttendanceStateRule.LATE.getLimit()) {
            return AttendanceStateRule.LATE;
        }

        return AttendanceStateRule.ATTEND;
    }

    private void validate(LocalTime time) {
        validateEnterTime(time);
    }

    private static void validateEnterTime(LocalTime time) {
        AttendanceTimeRule.validateEnterTime(time);
    }
}
