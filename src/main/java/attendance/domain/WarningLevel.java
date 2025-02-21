package attendance.domain;

import java.util.Map;

public enum WarningLevel {
    REMOVE("제적"), COUNSELING("면담"), WARNING("경고"), NONE("해당 없음");

    private final String level;

    WarningLevel(final String level){
        this.level = level;
    }

    public static WarningLevel calculateLevel(final Map<AttendanceStatus, Integer> attendanceStatuses) {
        int absenceCount = attendanceStatuses.get(AttendanceStatus.ABSENCE);
        absenceCount += convertLateness(attendanceStatuses.get(AttendanceStatus.LATENESS));
        if(absenceCount >= 6) {
            return WarningLevel.REMOVE;
        }
        if(absenceCount >= 3) {
            return WarningLevel.COUNSELING;
        }
        if(absenceCount >= 2) {
            return WarningLevel.WARNING;
        }
        return WarningLevel.NONE;
    }

    private static int convertLateness(int latenessCount) {
        return latenessCount / 3;
    }

    public String getLevel() {
        return level;
    }

}
