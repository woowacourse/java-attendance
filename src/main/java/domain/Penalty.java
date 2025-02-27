package domain;

import java.util.Arrays;

public enum Penalty {
    EXPULSION(6, "제적"),
    INTERVIEW(3, "면담"),
    WARNING(2, "경고");

    private final Integer point;
    private final String name;

    Penalty(Integer point, String name) {
        this.point = point;
        this.name = name;
    }

    public static Penalty getPenaltyOf(Integer point) {
        return Arrays.stream(values())
                .filter(penalty -> penalty.point < point)
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return name;
    }
}
