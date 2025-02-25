package domain;

public enum AttendanceStandard {
    OPEN_TIME(8),
    CLOSE_TIME(23),

    NON_MONDAY_START_HOUR(10),
    MONDAY_START_HOUR(13),

    LATE_DEADLINE(5),
    ABSENT_DEADLINE(30)
    ;

    private final int time;

    AttendanceStandard(int time){
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }
}
