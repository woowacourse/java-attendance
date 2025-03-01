package attendance.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum AttendanceStatus {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENCE("결석", 30);

    private final String title;
    private final int threshold;

    AttendanceStatus(
        final String title,
        final int threshold
    ) {
        this.title = title;
        this.threshold = threshold;
    }

    public static AttendanceStatus from(final AttendanceDateTime attendanceDateTime) {
        final Integer hour = attendanceDateTime.getAttendanceTime()
            .getHour()
            .orElse(null);
        final Integer minute = attendanceDateTime.getAttendanceTime()
            .getMinute()
            .orElse(null);

        final int startHour = attendanceDateTime.getAttendanceDate()
            .getAttendanceDayOfWeekDayOfWeek()
            .getStartHour();

        return caculateAttendanceStatus(hour, minute, startHour);
    }

    private static AttendanceStatus caculateAttendanceStatus(
        final Integer hour,
        final Integer minute,
        final int startHour
    ) {
        if (hour == null || minute == null) {
            return ABSENCE;
        }

        if (hour > startHour ||
            (hour.equals(startHour) && minute > ABSENCE.threshold)) {
            return ABSENCE;
        }

        if (hour.equals(startHour) && minute > LATE.threshold) {
            return LATE;
        }

        return ATTENDANCE;
    }

    public static Map<AttendanceStatus, Integer> from(final List<AttendanceDateTime> attendanceDateTimes) {
        return attendanceDateTimes.stream()
            .map(AttendanceStatus::from)
            .collect(Collectors.groupingBy(status -> status,
                Collectors.summingInt(e -> 1)));
    }

    public String getTitle() {
        return title;
    }
}
