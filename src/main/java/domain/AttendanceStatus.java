package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    ;

    public static final int LATE_LIMIT_IN_MINUTES = 5;
    public static final int ABSENT_LIMIT_IN_MINUTES = 30;
    private final String description;

    AttendanceStatus(String description) {
        this.description = description;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime time) {
        LectureTime.validateLectureDate(date);

        int elapsedMinutes = LectureTime.calculateElapsedMinutes(date, time);
        return of(elapsedMinutes);
    }

    private static AttendanceStatus of(int elapsedMinutes) {
        if (elapsedMinutes <= LATE_LIMIT_IN_MINUTES) {
            return ATTENDANCE;
        } else if (elapsedMinutes <= ABSENT_LIMIT_IN_MINUTES) {
            return LATE;
        }
        return ABSENT;
    }

    public String getDescription() {
        return description;
    }
}