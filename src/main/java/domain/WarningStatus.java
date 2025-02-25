package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum WarningStatus {
    CLEAR(0), WARNING(2), INTERVIEW(3), EXPEL(6);

    public static final long LATE_FER_ABSENCE = 3;

    private final int absenceCount;

    WarningStatus(final int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static WarningStatus judgeWarningStatus(AttendCount attendCount) {
        final long totalAbsenceCount = calculateTotalAbsenceCount(attendCount);
        return getWarningStatusOrderByAbsenceCountDesc().stream()
                .filter(warningStatus -> totalAbsenceCount >= warningStatus.absenceCount)
                .findFirst()
                .orElse(CLEAR);
    }

    private static long calculateTotalAbsenceCount(AttendCount attendCount) {
        return attendCount.late() / 3 + attendCount.absence();
    }

    private static List<WarningStatus> getWarningStatusOrderByAbsenceCountDesc() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing(WarningStatus::getAbsenceCount).reversed())
                .toList();
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
