package attendance;

import java.time.LocalTime;

public record AttendanceTime(
        LocalTime time
) {
    public AttendanceTime {
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException();
        }
    }

    public AttendanceStatus checkMondayAttendanceStatus() {
        if (time.isAfter(LocalTime.of(13, 30))) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(LocalTime.of(13, 5))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTEND;
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
}
