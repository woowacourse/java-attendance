package domain;

public enum Calendar {
    DECEMBER(12, 1, 31),
    ;

    public final int month;
    public final int startDay;
    public final int endDay;

    Calendar(int month, int startDay, int endDay) {
        this.month = month;
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
