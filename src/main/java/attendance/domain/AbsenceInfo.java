package attendance.domain;

public class AbsenceInfo {
    private final int absenceCount;
    private final int lateCount;

    public AbsenceInfo(int absenceCount, int lateCount) {
        this.absenceCount = absenceCount;
        this.lateCount = lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int calculateTotalAbsence() {
        return absenceCount + (lateCount / 3);
    }
}
