package domain;

public enum WorkingTime {
    MONDAY(13, 0, 18, 0),
    TUESDAY(10, 0, 18, 0),
    WEDNESDAY(10, 0, 18, 0),
    THURSDAY(10, 0, 18, 0),
    FRIDAY(10, 0, 18, 0),
    ;

    private final int startHour;
    private final int startMinute;
    private final int endHour;
    private final int endMinute;

    WorkingTime(int startHour,
                int startMinute,
                int endHour,
                int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }


}
