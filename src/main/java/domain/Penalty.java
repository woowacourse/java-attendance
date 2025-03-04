package domain;

public enum Penalty {
    WARNING("경고", 2),
    COUNSELLING("면담", 3),
    EXPEL("제적", 5),

    NONE("", 0);

    private final String status;
    private final int count;

    Penalty(String status, int count) {
        this.status = status;
        this.count = count;
    }

    public static Penalty getPenalty(int penaltyStandard) {
        if (penaltyStandard > EXPEL.getCount()) {
            return EXPEL;
        }
        if (penaltyStandard >= COUNSELLING.getCount()) {
            return COUNSELLING;
        }
        if (penaltyStandard >= WARNING.getCount()) {
            return WARNING;
        }
        return NONE;
    }

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }
}
