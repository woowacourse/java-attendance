package attendance.domain;

import static attendance.domain.CampusOperatingRule.CAMPUS_CLOSE_HOUR;
import static attendance.domain.CampusOperatingRule.CAMPUS_OPEN_HOUR;
import java.time.LocalTime;

public record AttendanceTime(
        LocalTime time
) {
    public static AttendanceTime from(final LocalTime time) {
        validate(time);
        return new AttendanceTime(time);
    }

    public AttendanceStatus checkAttendanceStatus(LocalTime absenceThreshold, LocalTime lateThreshold) {
        if (time.isAfter(absenceThreshold)) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(lateThreshold)) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTEND;
    }

    private static void validate(final LocalTime time) {
        if (time.isBefore(CAMPUS_OPEN_HOUR.getTime()) || time.isAfter(CAMPUS_CLOSE_HOUR.getTime())) {
            throw new IllegalArgumentException();
        }
    }
}
