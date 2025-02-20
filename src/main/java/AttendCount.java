public record AttendCount(int attend, int late, int absence) {
    public WarningStatus judgeWarning() {
        int totalAbsenceCount = calculateTotalAbsenceCount();
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

    private int calculateTotalAbsenceCount() {
        return late / 3 + absence;
    }
}
