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

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }
}
