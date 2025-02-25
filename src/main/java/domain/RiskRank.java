package domain;

public enum RiskRank {
    NOT_MANAGED("", 0),
    WARNING("경고", 2),
    INTERVIEW("면담", 3),
    EXPELLED("제적", 6),
    ;

    private final String name;
    private final int absentLimit;

    RiskRank(String name, int absentLimit) {
        this.name = name;
        this.absentLimit = absentLimit;
    }
}
