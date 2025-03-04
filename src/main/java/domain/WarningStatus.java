package domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum WarningStatus {
    PASS(totalCount -> totalCount < WarningStatus.WARNING_BOUND),
    WARNING(totalCount -> totalCount == WarningStatus.WARNING_BOUND),
    INTERVIEW(totalCount -> WarningStatus.INTERVIEW_BOUND <= totalCount && totalCount <= WarningStatus.EXPEL_BOUND),
    EXPEL(totalCount -> totalCount > WarningStatus.EXPEL_BOUND);

    private static final long LATE_ABSENCE_RATIO = 3;
    private static final long WARNING_BOUND = 2;
    private static final long INTERVIEW_BOUND = 3;
    private static final long EXPEL_BOUND = 5;

    private Predicate<Long> condition;

    WarningStatus(Predicate<Long> condition) {
        this.condition = condition;
    }

    public static WarningStatus judgeWarningStatus(final AttendCount attendCount) {
        long totalAbsence = calculateTotalAbsence(attendCount);
        return Arrays.stream(WarningStatus.values())
                .filter(warningStatus -> warningStatus.condition.test(totalAbsence))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 판정 실패"));
    }

    private static long calculateTotalAbsence(final AttendCount attendCount) {
        return attendCount.lateCount() / LATE_ABSENCE_RATIO + attendCount.absenceCount();
    }
}
