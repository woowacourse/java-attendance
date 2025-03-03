package domain;

public enum DisciplinaryStatus {
    NONE("해당 사항 없음", 0),
    WARNING("경고", 6),
    ONE_ON_ONE("면담", 9),
    EXPELLED("제적", 15);

    private static final int ABSENCE_WEIGHT = 3;
    
    private final String name;
    private final int thresholdCount;

    DisciplinaryStatus(String name, int thresholdCount) {
        this.name = name;
        this.thresholdCount = thresholdCount;
    }

    public static DisciplinaryStatus of(int tardyCount, int absentCount) {
        if (convertToThresholdCount(absentCount, tardyCount) > EXPELLED.thresholdCount) {
            return EXPELLED;
        }
        if (convertToThresholdCount(absentCount, tardyCount) >= ONE_ON_ONE.thresholdCount) {
            return ONE_ON_ONE;
        }
        if (convertToThresholdCount(absentCount, tardyCount) >= WARNING.thresholdCount) {
            return WARNING;
        }
        return NONE;
    }

    private static int convertToThresholdCount(int absentCount, int tardyCount) {
        return absentCount * ABSENCE_WEIGHT + tardyCount;
    }
}
