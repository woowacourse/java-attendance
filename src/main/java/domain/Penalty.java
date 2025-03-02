package domain;

import java.util.Arrays;

public enum Penalty {

    NONE(0, 0),
    WARNING(1, 2),
    INTERVIEW(2, 3),
    EXPLUSION(3, 6);

    private final int code;
    private final int limit;

    Penalty(final int code, final int limit) {
        this.code = code;
        this.limit = limit;
    }

    public static Penalty findByAbsenceCount(final int count) {
        return Arrays.stream(Penalty.values())
                .sorted((p1,p2) -> p2.limit - p1.limit)
                .filter(penalty -> count >= penalty.limit)
                .findAny()
                .orElse(Penalty.NONE);
    }

    public int getCode() {
        return code;
    }
}
