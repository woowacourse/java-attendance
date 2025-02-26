package domain;

import java.util.Arrays;

public enum ExpulsionStatus {
    NORMAL("정상", 0),
    ADVANCE("경고", 2),
    INTERVIEW("면담", 3),
    EXPULSTION("제적", 6);

    String name;
    int boundary;

    ExpulsionStatus(final String name, final int boundary) {
        this.name = name;
        this.boundary = boundary;
    }

    public static ExpulsionStatus of(final int absenceCount) {
        return Arrays.stream(ExpulsionStatus.values())
                .sorted((o1, o2) -> o2.boundary - o1.boundary)
                .filter(status -> status.boundary <= absenceCount)
                .findFirst()
                .orElse(NORMAL);
    }

    public String getName() {
        return name;
    }
}
