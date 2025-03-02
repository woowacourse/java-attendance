package domain;

public enum Penalty {
    EXPELLED("제적", 6),
    COUNSELING("면담", 3),
    WARNING("경고", 2),
    PASS("패스", 0);

    private final String penalty;
    private final int count;

    Penalty(String penalty, int count) {
        this.penalty = penalty;
        this.count = count;
    }

    public static Penalty of(int lateCount, int absentCount) {
        // TODO: 패널티 계산 구현
        return null;
    }
}
