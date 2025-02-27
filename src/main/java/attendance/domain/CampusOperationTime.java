package attendance.domain;

public enum CampusOperationTime {
    OPEN(8),
    CLOSE(23);

    private final int hour;

    CampusOperationTime(final int hour) {
        this.hour = hour;
    }

    public static boolean isOperation(final int inputHour) {
        return (OPEN.hour <=  inputHour) && (CLOSE.hour > inputHour);
    }
}
