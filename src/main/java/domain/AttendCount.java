package domain;

public record AttendCount(long attend, long late, long absence) {
    public WarningStatus judgeWarning() {
        return WarningStatus.judgeWarningStatus(this);
    }

    public long calculateRank() {
        return absence * WarningStatus.LATE_FER_ABSENCE + late;
    }
}
