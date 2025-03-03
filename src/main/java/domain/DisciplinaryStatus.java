package domain;

public enum DisciplinaryStatus {
    NONE("해당 사항 없음", 1),
    WARNING("경고", 2),
    ONE_ON_ONE("면담", 3),
    EXPELLED("제적", 5);

    private final String name;
    private final int thresholdCount;

    DisciplinaryStatus(String name, int thresholdCount) {
        this.name = name;
        this.thresholdCount = thresholdCount;
    }

    public static DisciplinaryStatus of(int tardyCount, int absentCount) {
        if (convertTardyToAbsent(absentCount, tardyCount) > EXPELLED.thresholdCount) {
            return EXPELLED;
        }
        if (convertTardyToAbsent(absentCount, tardyCount) >= ONE_ON_ONE.thresholdCount) {
            return ONE_ON_ONE;
        }
        if (convertTardyToAbsent(absentCount, tardyCount) >= WARNING.thresholdCount) {
            return WARNING;
        }
        return NONE;
    }

    private static int convertTardyToAbsent(int absentCount, int tardyCount) {
        int totalCount = absentCount;
        totalCount += (tardyCount / 3);
        tardyCount -= (tardyCount / 3);
        totalCount += tardyCount % 3;
        return totalCount;
    }
}
