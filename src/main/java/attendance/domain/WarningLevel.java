package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;

import java.util.Map;

public enum WarningLevel {
    EXPELLED("제적"), SUPERVISED("면담"), WARNING("경고"), NONE("해당 없음");

    private final String displayName;

    WarningLevel(final String displayName) {
        this.displayName = displayName;
    }

    public static WarningLevel calculateLevel(final Map<AttendanceStatus, Integer> attendanceStatusCounts) {
        int absenceCount = attendanceStatusCounts.get(ABSENCE);
        absenceCount += countAsAbsence(attendanceStatusCounts.get(LATENESS));
        if (absenceCount >= 6) {
            return EXPELLED;
        }
        if (absenceCount >= 3) {
            return SUPERVISED;
        }
        if (absenceCount >= 2) {
            return WARNING;
        }
        return WarningLevel.NONE;
    }

    private static int countAsAbsence(int latenessCount) {
        return latenessCount / 3;
    }

    public String getDisplayName() {
        return displayName;
    }

}
