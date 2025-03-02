package domain;

public enum AlertCode {
    NORMAL(0, "없음"),
    WARNING(2, "경고"),
    COUNSELING(3, "면담"),
    EXPULSION(6, "제적"),
    ;

    private final int limit;
    private final String name;

    AlertCode(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }
}
