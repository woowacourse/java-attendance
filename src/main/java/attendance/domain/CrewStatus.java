package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.LATE;

import java.util.Map;

public enum CrewStatus {
    FIRE("제적", 5),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    CLEAR("통과", 0);

    private final String name;
    private final int decideStatusValue;

    CrewStatus(String name, int decideStatusValue) {
        this.name = name;
        this.decideStatusValue = decideStatusValue;
    }

    public String getName() {
        return name;
    }

    public static CrewStatus calculateCrewStatus(Map<AttendanceType, Integer> attendanceResult) {
        int validateValue = 0;
        validateValue += attendanceResult.get(ABSENCE);
        validateValue += attendanceResult.get(LATE) / 3;
        if (validateValue > FIRE.decideStatusValue) {
            return FIRE;
        }
        if (validateValue >= INTERVIEW.decideStatusValue) {
            return INTERVIEW;
        }
        if (validateValue >= WARNING.decideStatusValue) {
            return WARNING;
        }
        return CLEAR;
    }

}
