package domain;

public enum AlertCode {
    NORMAL(0),
    WARNING(2),
    COUNSELING(3),
    EXPULSION(6),
    ;

    private final int limit;

    AlertCode(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
