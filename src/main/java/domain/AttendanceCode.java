package domain;

public enum AttendanceCode {
    PRESENT(0, 5),
    LATE(6, 30),
    ABSENT(31, 59),
    ;

    private final int lowerBound;
    private final int upperBound;

    AttendanceCode(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
