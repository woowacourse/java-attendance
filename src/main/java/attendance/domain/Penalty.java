package attendance.domain;

public enum Penalty {
    REMOVAL("제적"),
    INTERVIEW("면담"),
    WARNING("경고"),
    NONE("");

    private String koreanName;

    Penalty(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Penalty determine(int absenceCount, int lateCount) {
        absenceCount = absenceCount + lateCount / 3;
        if (absenceCount > 5) {
            return Penalty.REMOVAL;
        }
        if (absenceCount >= 3) {
            return Penalty.INTERVIEW;
        }
        if (absenceCount >= 2) {
            return Penalty.WARNING;
        }
        return Penalty.NONE;
    }

}
