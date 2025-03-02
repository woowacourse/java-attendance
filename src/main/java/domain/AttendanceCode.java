package domain;

public enum AttendanceCode {
    PRESENT(0, 5, "출석"),
    LATE(6, 30, "지각"),
    ABSENT(31, 59, "결석"),
    ;

    private final int lowerBound;
    private final int upperBound;
    private final String name;

    AttendanceCode(int lowerBound, int upperBound, String name) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.name = name;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public String getName() {
        return name;
    }
}
