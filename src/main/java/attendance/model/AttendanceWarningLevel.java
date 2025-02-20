package attendance.model;

import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceWarningLevel {

    CLEAN((totalAbsentCount) -> totalAbsentCount < 2),
    WARNING((totalAbsentCount) -> totalAbsentCount == 2),
    MEETING((totalAbsentCount) -> totalAbsentCount >= 3 && totalAbsentCount <= 5),
    EXPULSION((totalAbsentCount) -> totalAbsentCount > 5),
    ;

    private final Function<Integer, Boolean> isMatch;

    AttendanceWarningLevel(Function<Integer, Boolean> isMatch) {
        this.isMatch = isMatch;
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
}
