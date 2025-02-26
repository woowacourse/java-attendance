package policy.time;

import policy.time.rule.AttendanceStateRule;
import policy.time.rule.CampusTimeRule;
import policy.time.rule.StudyTimeRule;

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
