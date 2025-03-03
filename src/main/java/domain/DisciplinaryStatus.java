package domain;

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
}
