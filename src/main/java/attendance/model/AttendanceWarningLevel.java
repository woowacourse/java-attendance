package attendance.model;

import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceWarningLevel {

    CLEAN((totalAbsentCount) -> totalAbsentCount < 2, 0),
    WARNING((totalAbsentCount) -> totalAbsentCount == 2, 1),
    MEETING((totalAbsentCount) -> totalAbsentCount >= 3 && totalAbsentCount <= 5, 2),
    EXPULSION((totalAbsentCount) -> totalAbsentCount > 5, 3),
    ;

    private final Function<Integer, Boolean> isMatch;

    private final int importance;

    AttendanceWarningLevel(Function<Integer, Boolean> isMatch, int importance) {
        this.isMatch = isMatch;
        this.importance = importance;
    }

    public static AttendanceWarningLevel judge(int lateCount, int absenceCount) {
        int totalAbsentCount = calculateLateToAbsent(lateCount) + absenceCount;
        return Arrays.stream(values())
                .filter(level -> level.isMatch.apply(totalAbsentCount))
                .findFirst()
                .orElse(CLEAN);
    }

    public static int calculateLateToAbsent(int lateCount) {
        return lateCount / 3;
    }

    public int getImportance() {
        return importance;
    }
}
