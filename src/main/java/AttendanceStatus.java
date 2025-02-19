import java.time.LocalTime;

public enum AttendanceStatus {
    ATTEND("(출석)"),
    ABSENT("(결석)"),
    LATE("(지각)"),
    NONE("");

    AttendanceStatus(String message) {
        this.message = message;
    }

    private final String message;

    public AttendanceStatus getInMonday(LocalTime time) {
        if (!time.isBefore(LocalTime.of(8, 0)) && !time.isAfter(LocalTime.of(13, 0))) {
            return ATTEND;
        }
        if (!time.isBefore(LocalTime.of(13, 0)) && !time.isAfter(LocalTime.of(13, 30))) {
            return LATE;
        }
        if (!time.isBefore(LocalTime.of(13, 30)) && !time.isAfter(LocalTime.of(23, 0))) {
            return ABSENT;
        }
        return NONE;
    }

    public AttendanceStatus getExceptMonday(LocalTime time) {
        if (!time.isBefore(LocalTime.of(8, 0)) && !time.isAfter(LocalTime.of(10, 0))) {
            return ATTEND;
        }
        if (!time.isBefore(LocalTime.of(10, 0)) && !time.isAfter(LocalTime.of(10, 30))) {
            return LATE;
        }
        if (!time.isBefore(LocalTime.of(10, 30)) && !time.isAfter(LocalTime.of(23, 0))) {
            return ABSENT;
        }
        return NONE;
    }
}