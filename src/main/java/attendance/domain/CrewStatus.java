package attendance.domain;

import java.util.Map;

public enum CrewStatus {
    WARING, INTERVIEW, FIRE, NORMAL;

    public static CrewStatus calculate(Map<AttendanceType, Integer> attendanceResult) {
        return WARING;
    }
}
