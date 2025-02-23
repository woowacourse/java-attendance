package constant;

public enum Warning {
    EXPEL("제적", 5),
    COUNSELING("면담",3),
    WARNING("경고", 2)
    ;

    private final String penalty;
    private final int absentCount;

    Warning(String penalty, int absentCount) {
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
