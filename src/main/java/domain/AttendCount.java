package domain;

public record AttendCount(long attend, long late, long absence) {
    public WarningStatus judgeWarning() {
        long totalAbsenceCount = calculateTotalAbsenceCount();
        if (totalAbsenceCount > 5) {
            return WarningStatus.EXPEL;
        }
        if (totalAbsenceCount >= 3) {
            return WarningStatus.INTERVIEW;
        }
        if (totalAbsenceCount >= 2) {
            return WarningStatus.WARNING;
        }
        return WarningStatus.CLEAR;
    }

    public long calculateRank() {
        return absence * 3 + late;
    }

    private long calculateTotalAbsenceCount() {
        return late / 3 + absence;
    }
}
