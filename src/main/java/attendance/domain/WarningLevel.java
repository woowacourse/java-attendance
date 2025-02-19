package attendance.domain;

import java.util.Map;

public enum WarningLevel {
    REMOVE("제적"), COUNSELING("면담"), WARNING("경고"), NONE("해당 없음");

    private String level;

    WarningLevel(final String level){
        this.level = level;
    }

    public static WarningLevel calculateLevel(final Map<AttendanceStatus, Integer> attendanceStatuses) {
        int absenceCount = attendanceStatuses.get(AttendanceStatus.ABSENCE);
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

    public String getLevel() {
        return level;
    }

}
