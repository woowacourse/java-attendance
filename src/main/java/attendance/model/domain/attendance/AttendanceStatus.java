package attendance.model.domain.attendance;

import attendance.model.Calender;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    private static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);

    private static final LocalTime WEEKDAY_LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime WEEKDAY_ABSENCE_TIME = LocalTime.of(10, 30);

    private final String name;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public static AttendanceStatus fromDateTime(final LocalDateTime dateTime) {
        if (isAbsence(dateTime)) {
            return ABSENCE;
        }
        if (isLate(dateTime)) {
            return LATE;
        }
        return ATTENDANCE;
    }

    public static boolean isAbsence(final LocalDateTime dateTime) {
        final LocalTime time = dateTime.toLocalTime();
        if (Calender.isMonday(dateTime.toLocalDate())) {
            return time.isAfter(MONDAY_ABSENCE_TIME);
        }
        return time.isAfter(WEEKDAY_ABSENCE_TIME);
    }

    public static boolean isLate(final LocalDateTime dateTime) {
        if (Calender.isMonday(dateTime.toLocalDate())) {
            return isTimeBetween(dateTime.toLocalTime(), MONDAY_LATE_TIME, MONDAY_ABSENCE_TIME.plusMinutes(1));
        }
        return isTimeBetween(dateTime.toLocalTime(), WEEKDAY_LATE_TIME, WEEKDAY_ABSENCE_TIME.plusMinutes(1));
    }

    public static List<String> getNames() {
        return Arrays.stream(values())
                .map(AttendanceStatus::getName)
                .toList();
    }

    private static boolean isTimeBetween(final LocalTime time, final LocalTime startTime, final LocalTime endTime) {
        return time.isAfter(startTime) && time.isBefore(endTime);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AttendanceStatus{" +
                "name='" + name + '\'' +
                '}';
    }
}
