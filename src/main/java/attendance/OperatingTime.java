package attendance;

import java.time.LocalTime;

public enum OperatingTime {

    OPERATING_TIME(LocalTime.of(8, 0), LocalTime.of(23, 0));

    private final LocalTime operatingStartTime;
    private final LocalTime operatingEndTime;

    OperatingTime(LocalTime operatingStartTime, LocalTime operatingEndTime) {
        this.operatingStartTime = operatingStartTime;
        this.operatingEndTime = operatingEndTime;
    }

    public static boolean isOperating(LocalTime validateTime) {
        if (validateTime.isBefore(OperatingTime.OPERATING_TIME.operatingStartTime) ||
            validateTime.isAfter(OperatingTime.OPERATING_TIME.operatingEndTime)) {
            return false;
        }
        return true;
    }

}
