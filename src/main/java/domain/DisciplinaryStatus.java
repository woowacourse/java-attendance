package domain;

import java.util.Arrays;

public enum DisciplinaryStatus {
    EXPELLED("제적", 6),
    ONE_ON_ONE("면담", 3),
    WARNING("경고", 2),
    NONE("해당 사항 없음", 1);

    private final String name;
    private final int thresholdCount;

    DisciplinaryStatus(String name, int thresholdCount) {
        this.name = name;
        this.thresholdCount = thresholdCount;
    }

    public static DisciplinaryStatus getStatus(int absentCount, int tardyCount) {
        final int convertedAbsences = convertTardyToAbsent(absentCount, tardyCount);
        return Arrays.stream(DisciplinaryStatus.values())
                .filter(status -> convertedAbsences >= status.thresholdCount)
                .findFirst()
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }

    public static int getConvertedAbsencesAndTardies(int absentCount, int tardyCount) {
        int convertedAbsences = convertTardyToAbsent(absentCount, tardyCount);
        return convertedAbsences + tardyCount % 3;
    }

    private static int convertTardyToAbsent(int absentCount, int tardyCount) {
        return absentCount + (tardyCount / 3);
    }
}
