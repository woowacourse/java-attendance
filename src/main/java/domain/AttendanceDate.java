package domain;

public class AttendanceDate {
    private final int month = 12;
    private final int day;

    public AttendanceDate(int day) {
        this.day = day;
    }

    public int getDayOfWeek() {
        return ((day + 5) % 7) + 1;
    }
}
