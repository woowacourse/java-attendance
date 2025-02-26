package attendance.model;

public enum Panalty {
    INTERVIEW,
    WARN,
    DISMISSAL;

    public static Panalty of(int absenceCount, int lateCount) {
        return null;
    }
}
