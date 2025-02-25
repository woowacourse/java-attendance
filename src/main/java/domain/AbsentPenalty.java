package domain;

public enum AbsentPenalty {
    EXPEL("제적", 5),
    COUNSELING("면담",3),
    WARNING("경고", 2),
    NONE("", 0)
    ;

    private final String penalty;
    private final int absentCount;

    AbsentPenalty(String penalty, int absentCount) {
        this.penalty = penalty;
        this.absentCount = absentCount;
    }


    public String getPenalty() {
        return this.penalty;
    }

    public int getAbsentCount() {
        return absentCount;
    }
}
