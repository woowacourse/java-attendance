package domain;

public enum Penalty {

    WARNING(1, 2),
    INTERVIEW(2, 3),
    EXPLUSION(3, 5);

    private final int code;
    private final int limit;

    Penalty(final int code, final int limit) {
        this.code = code;
        this.limit = limit;
    }

    public static Penalty findByAbsenceCount(final int count) {
        return null;
    }
}
