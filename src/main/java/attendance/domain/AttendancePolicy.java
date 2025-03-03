package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class AttendancePolicy {
    private static final LocalTime MONDAY_ABSENCE_THRESHOLD = LocalTime.of(13, 30);
    private static final LocalTime MONDAY_LATE_THRESHOLD = LocalTime.of(13, 5);
    private static final LocalTime GENERAL_ABSENCE_THRESHOLD = LocalTime.of(10, 30);
    private static final LocalTime GENERAL_LATE_THRESHOLD = LocalTime.of(10, 5);

    private AttendancePolicy() {
    }

    public static AttendanceStatus calculateAttendanceStatus(final DayOfWeek attendanceDay,
                                                             final LocalTime attendanceTime) {
        boolean isMonday = attendanceDay == DayOfWeek.MONDAY;
        if (isMonday) {
            return calculateStatus(attendanceTime, MONDAY_ABSENCE_THRESHOLD, MONDAY_LATE_THRESHOLD);
        }
        return calculateStatus(attendanceTime, GENERAL_ABSENCE_THRESHOLD, GENERAL_LATE_THRESHOLD);
    }

    private static AttendanceStatus calculateStatus(final LocalTime attendanceTime,
                                                    final LocalTime absenceThreshold,
                                                    final LocalTime lateThreshold) {
        if (attendanceTime.isAfter(absenceThreshold)) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(lateThreshold)) {
            return LATE;
        }
        return ATTENDANCE;
    }
}
