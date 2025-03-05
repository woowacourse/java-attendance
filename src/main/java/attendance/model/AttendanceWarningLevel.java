package attendance.model;

import java.util.Arrays;
import java.util.function.Predicate;

public enum AttendanceWarningLevel {

    CLEAN("정상", absentTotal -> absentTotal < 2),
    WARNING("경고", absentTotal -> absentTotal == 2),
    MEETING("면담", absentTotal -> 3 <= absentTotal && absentTotal <= 5),
    EXPULSION("제적", absentTotal -> 5 < absentTotal),
    ;

    private static final int LATE_TO_ABSENT_THRESHOLD = 3;

    private final String koreanLabel;
    private final Predicate<Integer> condition;

    AttendanceWarningLevel(String koreanLabel, Predicate<Integer> condition) {
        this.koreanLabel = koreanLabel;
        this.condition = condition;
    }

    public static AttendanceWarningLevel determine(int lateCount, int absenceCount) {
        int totalAbsentCount = calculateLateToAbsent(lateCount) + absenceCount;
        return Arrays.stream(values())
                .filter(level -> level.condition.test(totalAbsentCount))
                .findFirst()
                .orElse(CLEAN);
    }

    private static int calculateLateToAbsent(int lateCount) {
        return lateCount / LATE_TO_ABSENT_THRESHOLD;
    }

    public String getKoreanLabel() {
        return koreanLabel;
    }
}
