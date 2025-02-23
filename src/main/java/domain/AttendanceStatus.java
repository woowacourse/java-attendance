package domain;

public enum AttendanceStatus {
    PRESENT(0, 5, "출석"),
    LATE(5, 30, "지각"),
    ABSENT(30, 0, "결석");

    final int lowerBound;
    final int upperBound;
    final String name;

    AttendanceStatus(int lowerBound, int upperBound, String name) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
