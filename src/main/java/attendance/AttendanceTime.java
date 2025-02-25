package attendance;

import java.time.LocalTime;

public record AttendanceTime(
        LocalTime time
) {
    public static AttendanceTime from(LocalTime time) {
        validate(time);
        return new AttendanceTime(time);
    }

    public AttendanceStatus checkAttendanceStatus(boolean isMonday) {
        LocalTime absenceThreshold = LocalTime.of(10, 30);
        LocalTime lateThreshold = LocalTime.of(10, 5);
        if (isMonday) {
            absenceThreshold = absenceThreshold.withHour(13);
            lateThreshold = lateThreshold.withHour(13);
        }
        if (time.isAfter(absenceThreshold)) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(lateThreshold)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTEND;
    }

    private static void validate(LocalTime time) {
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException();
        }
    }
}
