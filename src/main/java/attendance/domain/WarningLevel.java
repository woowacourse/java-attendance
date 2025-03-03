package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENT;
import static attendance.domain.AttendanceStatus.LATE;

import java.util.Arrays;
import java.util.Map;

public enum WarningLevel {
    EXPELLED("제적", 6),
    ONE_ON_ONE("면담", 3),
    WARNING("경고", 2),
    NONE("해당없음", 0);

    private final String displayName;
    private final int absentCountUnderBound;

    WarningLevel(final String displayName, final int absentCountUnderBound) {
        this.displayName = displayName;
        this.absentCountUnderBound = absentCountUnderBound;
    }

    public static WarningLevel calculateBy(final Map<AttendanceStatus, Integer> attendanceStatusCounts) {
        int absentCount = calculateAbsentCount(attendanceStatusCounts);
        return getWarningLevelBy(absentCount);
    }

    private static int calculateAbsentCount(final Map<AttendanceStatus, Integer> attendanceStatusCounts) {
        return attendanceStatusCounts.get(ABSENT) + countAsAbsent(attendanceStatusCounts.get(LATE));
    }

    private static int countAsAbsent(final int lateCount) {
        return lateCount / 3;
    }

    private static WarningLevel getWarningLevelBy(final int absentCount) {
        return Arrays.stream(WarningLevel.values())
                .filter(warningLevel -> isMatch(absentCount, warningLevel))
                .findFirst()
                .orElse(WarningLevel.NONE);
    }

    private static boolean isMatch(final int absentCount, final WarningLevel warningLevel) {
        return absentCount >= warningLevel.absentCountUnderBound;
    }

    public String getDisplayName() {
        return displayName;
    }
}
