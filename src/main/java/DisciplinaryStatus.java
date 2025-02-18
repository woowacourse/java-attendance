public enum DisciplinaryStatus {
    NONE("해당 사항 없음", 1),
    WARNING("경고", 2),
    ONE_ON_ONE("면담", 3),
    EXPELLED("제적", 6);

    private final String name;
    private final int thresholdCount;

    DisciplinaryStatus(String name, int thresholdCount) {
        this.name = name;
        this.thresholdCount = thresholdCount;
    }

    public static DisciplinaryStatus getStatus(int absentCount, int tardyCount) {
        if (calculateTotalAbsentCount(absentCount, tardyCount) >= EXPELLED.thresholdCount) {
            return EXPELLED;
        }
        if (calculateTotalAbsentCount(absentCount, tardyCount) >= ONE_ON_ONE.thresholdCount) {
            return ONE_ON_ONE;
        }
        if (calculateTotalAbsentCount(absentCount, tardyCount) >= WARNING.thresholdCount) {
            return WARNING;
        }
        return NONE;
    }

    private static int calculateTotalAbsentCount(int absentCount, int tardyCount) {
        return absentCount + (tardyCount / 3);
    }
}
