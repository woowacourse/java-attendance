package domain;

public record AttendCount(long attend, long late, long absence) {

    private static final int ABSENCE_LATE_RATIO = 3;

    public WarningStatus judgeWarning() {
        long totalAbsenceCount = calculateTotalAbsenceCount();
        return WarningStatus.judgeWarningStatus(totalAbsenceCount);
    }

    public long calculateRank() {
        return absence * ABSENCE_LATE_RATIO + late;
    }

    private long calculateTotalAbsenceCount() {
        return late / ABSENCE_LATE_RATIO + absence;
    }
}
