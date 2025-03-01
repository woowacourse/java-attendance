package attendance.domain;

import static attendance.domain.AttendanceType.*;

import java.util.Map;

public enum CrewStatus {
    WARING, INTERVIEW, FIRE, NORMAL;

    public static CrewStatus calculate(Map<AttendanceType, Integer> attendanceResult) {
        int statusDecisionValue = attendanceResult.get(LATE) / 3 + attendanceResult.get(ABSENCE);
        if (statusDecisionValue >= 3) {
            return INTERVIEW;
        }
        if (statusDecisionValue >= 2) {
            return WARING;
        }
        return NORMAL;
    }
}
