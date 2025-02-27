package attendance.domain;

import java.time.LocalTime;

public enum CampusOperatingRule {
    CAMPUS_OPEN_HOUR(LocalTime.of(8, 0)),
    CAMPUS_CLOSE_HOUR(LocalTime.of(23, 0)),
    DEFAULT_ABSENCE_THRESHOLD(LocalTime.of(10, 30)),
    DEFAULT_LATE_THRESHOLD(LocalTime.of(10, 5)),
    MONDAY_ABSENCE_THRESHOLD(LocalTime.of(13, 30)),
    MONDAY_LATE_THRESHOLD(LocalTime.of(13, 5)),
    ;

    private final LocalTime time;

    CampusOperatingRule(LocalTime time) {
        this.time = time;
    }

    public static LocalTime getAbsenceThreshold(boolean isMonday) {
        if (isMonday) {
            return MONDAY_ABSENCE_THRESHOLD.time;
        }
        return DEFAULT_ABSENCE_THRESHOLD.time;
    }

    public static LocalTime getLateThreshold(boolean isMonday) {
        if (isMonday) {
            return MONDAY_LATE_THRESHOLD.time;
        }
        return DEFAULT_LATE_THRESHOLD.time;
    }

    public LocalTime getTime() {
        return time;
    }
}
