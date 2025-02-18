package domain;

public enum AttendanceStatus {
    PRESENT(0, 5),
    LATE(5, 30),
    ABSENT(30, 0);

    int lowerBound;
    int upperBound;

    AttendanceStatus(int lowerBound, int upperBound) {
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
