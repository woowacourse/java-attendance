package domain;

public enum AttendanceStatus {
    PRESENT(0, 5, "출석"),
    LATE(5, 30, "지각"),
    ABSENT(30, 0, "결석");

    int lowerBound;
    int upperBound;
    String name;

    AttendanceStatus(int lowerBound, int upperBound, String name) {
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
