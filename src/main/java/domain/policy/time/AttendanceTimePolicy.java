package domain.policy.time;

import domain.policy.time.rule.AttendanceStateRule;
import domain.policy.time.rule.CampusTimeRule;
import domain.policy.time.rule.StudyTimeRule;

import java.time.LocalTime;

public class AttendanceTimePolicy {

    public LocalTime getAttendStartTime(boolean isSpecialDay) {
        if (isSpecialDay) {
            return StudyTimeRule.SPECIAL_STUDY_START.toLocalTime();
        }
        return StudyTimeRule.NORMAL_STUDY_START.toLocalTime();
    }

    public boolean canAttendTime(LocalTime time) {
        return CampusTimeRule.canAttendTime(time);
    }

    public AttendanceStateRule decisionAttendanceState(long lateMinutes) {
        return AttendanceStateRule.decisionState(lateMinutes);
    }
}
