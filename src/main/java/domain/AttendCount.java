package domain;

import java.util.Arrays;

public record AttendCount(long attend, long late, long absence) {

    private static final int ABSENCE_LATE_RATIO = 3;

    public WarningStatus judgeWarning() {
        long totalAbsenceCount = calculateTotalAbsenceCount();
        return Arrays.stream(WarningStatus.values())
                .filter(warningStatus -> warningStatus.match(totalAbsenceCount))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public long calculateRank() {
        return absence * ABSENCE_LATE_RATIO + late;
    }

    private long calculateTotalAbsenceCount() {
        return late / ABSENCE_LATE_RATIO + absence;
    }
}
