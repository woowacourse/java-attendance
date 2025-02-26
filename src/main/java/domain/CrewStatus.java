package domain;

import java.util.Arrays;

public enum CrewStatus {
    NORMAL(2),
    WARNING(3),
    COUNSEL(6),
    EXPELLED(0);

    final int absentLimit;

    CrewStatus(int absentLimit) {
        this.absentLimit = absentLimit;
    }

    public static CrewStatus calculateCrewStatus(int absentTotal) {
        return Arrays.stream(CrewStatus.values())
                .filter(status -> status.absentLimit > absentTotal)
                .findFirst()
                .orElse(EXPELLED);
    }
}
