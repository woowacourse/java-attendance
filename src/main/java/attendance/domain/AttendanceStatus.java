package attendance.domain;

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

    public static AttendanceStatus from(final Attendance attendance) {
        final Integer hour = attendance.getAttendanceTime()
            .getHour()
            .orElse(null);
        final Integer minute = attendance.getAttendanceTime()
            .getMinute()
            .orElse(null);

        if (hour == null || minute == null) {
            return ABSENCE;
        }

        final int startHour = attendance.getAttendanceDate()
            .getAttendanceDayOfWeekDayOfWeek()
            .getStartHour();

        if (hour > startHour ||
            (hour.equals(startHour) && minute > ABSENCE.threshold)) {
            return ABSENCE;
        }

        if (hour > startHour ||
            (hour.equals(startHour) && minute > LATE.threshold)) {
            return LATE;
        }

        return ATTENDANCE;
    }
}
