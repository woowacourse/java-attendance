package domain;

import java.util.Arrays;

public enum WarningStatus {
    NONE("", 0),
    WARN("경고", 2),
    COUNSEL("면담", 3),
    EXPEL("제적", 6);

    private final String name;
    private final int minAbsenceCount;

    WarningStatus(String name, int minAbsenceCount) {
        this.name = name;
        this.minAbsenceCount = minAbsenceCount;
    }

    public static WarningStatus getStatus(int tardyCount, int absenceCount) {
        int convertedAbsences = convertTardiesToAbsences(tardyCount, absenceCount);
        return Arrays.stream(WarningStatus.values())
                .filter(status -> convertedAbsences >= status.minAbsenceCount)
                .reduce((first, second) -> second)
                .orElse(NONE);
    }

    private static int convertTardiesToAbsences(int tardyCount, int absenceCount) {
        return absenceCount + tardyCount / 3;
    }

    public String getName() {
        return name;
    }
}
