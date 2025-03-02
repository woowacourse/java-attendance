package attendance.domain;

import java.time.LocalTime;

public enum AttendanceStatus {
    LATE("지각"),
    ABSENCE("결석"),
    ATTEND("출석"),
    ;

    private final String message;

    AttendanceStatus(String message) {
        this.message = message;
    }

    public static AttendanceStatus of(
            final LocalTime attendTime,
            final LocalTime absenceThreshold,
            final LocalTime lateThreshold
    ) {
        if (attendTime.isAfter(absenceThreshold)) {
            return ABSENCE;
        }
        if (attendTime.isAfter(lateThreshold)) {
            return LATE;
        }
        return ATTEND;
    }

    public String getMessage() {
        return message;
    }
}
