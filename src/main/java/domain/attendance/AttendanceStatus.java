package domain.attendance;

public enum AttendanceStatus {
    PRESENCE(0),
    LATE(5),
    ABSENCE(30),
    ;

    private final int arrivalTimeLimit;

    AttendanceStatus(int arrivalTimeLimit) {
        this.arrivalTimeLimit = arrivalTimeLimit;
    }

    public static AttendanceStatus timeGapToAttendanceStatus(int minute) {
        if (minute <= LATE.arrivalTimeLimit) {
            return PRESENCE;
        }
        if (minute <= ABSENCE.arrivalTimeLimit) {
            return LATE;
        }
        return ABSENCE;
    }
}
