package attendance.domain;

import java.time.LocalTime;

public enum CampusOperatingTime {
    START(LocalTime.of(8, 0)),
    END(LocalTime.of(23, 0))
    ;

    private final LocalTime time;

    CampusOperatingTime(LocalTime time) {
        this.time = time;
    }

    public static boolean notInOperation(LocalTime inputTime) {
        return inputTime.isBefore(START.time) || inputTime.isAfter(END.time);
    }
}
