package domain.policy;

public enum PenaltyPolicy {
    EXPULSION("제적", 5),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NO_PENALTY("", 0);

    public static final int CONVERT_LATE_TO_ABSENT_CRITERIA = 3;
    private final String penalty;
    private final int criteria;

    PenaltyPolicy(String penalty, int criteria) {
        this.penalty = penalty;
        this.criteria = criteria;
    }

    public String getPenalty() {
        return penalty;
    }

    public int getCriteria() {
        return criteria;
    }

    // 패널티 정책 1. 패널티 횟수는 결석 횟수를 기준으로 하며, 지각 3회당 결석 1회로 간주한다.
    public static int calculatePenaltyCount(int absentCount, int lateCount) {
        return absentCount + lateCount / CONVERT_LATE_TO_ABSENT_CRITERIA;
    }
}