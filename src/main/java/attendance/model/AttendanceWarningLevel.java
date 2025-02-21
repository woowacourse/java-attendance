package attendance.model;

import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceWarningLevel {

    CLEAN("정상", totalAbsentCount -> totalAbsentCount < 2),
    WARNING("경고", totalAbsentCount -> totalAbsentCount == 2),
    MEETING("면담", totalAbsentCount -> totalAbsentCount >= 3 && totalAbsentCount <= 5),
    EXPULSION("제적", totalAbsentCount -> totalAbsentCount > 5),
    ;

    private final String label;
    private final Function<Integer, Boolean> isMatch;

    AttendanceWarningLevel(String label, Function<Integer, Boolean> isMatch) {
        this.label = label;
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

    public String getLabel() {
        return label;
    }
}
