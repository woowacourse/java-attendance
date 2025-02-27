package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.LATE;

import java.util.Map;

public enum CrewStatus {
    FIRE("제적", 5),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    CLEAR("통과", 0);

    private final String statusDescription;
    private final int statusDecisionValue;

    CrewStatus(String statusDescription, int statusDecisionValue) {
        this.statusDescription = statusDescription;
        this.statusDecisionValue = statusDecisionValue;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public static CrewStatus calculateCrewStatus(Map<AttendanceType, Long> attendanceResult) {
        long validateValue = attendanceResult.get(ABSENCE) + attendanceResult.get(LATE) / 3;
        if (validateValue > FIRE.statusDecisionValue) {
            return FIRE;
        }
        if (validateValue >= INTERVIEW.statusDecisionValue) {
            return INTERVIEW;
        }
        if (validateValue >= WARNING.statusDecisionValue) {
            return WARNING;
        }
        return CLEAR;
    }
}
