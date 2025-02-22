package domain;

import java.time.LocalDateTime;

import static domain.December.DEFAULT_MONTH;
import static domain.December.DEFAULT_YEAR;

public enum AttendanceType {

    ABSENT("결석"),
    LATE("지각"),
    ATTENDED("출석");


    public static final int LATE_TO_ABSENT_COUNT = 3;

    private final String type;

    AttendanceType(String type) {
        this.type = type;
    }

    public static AttendanceType checkTime(LocalDateTime attendTime) {
        final LocalDateTime lateTime = LocalDateTime.of(DEFAULT_YEAR, DEFAULT_MONTH, attendTime.getDayOfMonth(),
                getDayInfo(attendTime.getDayOfMonth()), 5, 0);

        final LocalDateTime absentTime = LocalDateTime.of(DEFAULT_YEAR, DEFAULT_MONTH, attendTime.getDayOfMonth(),
                getDayInfo(attendTime.getDayOfMonth()), 30, 0);

        if (attendTime.isAfter(absentTime)) {
            return ABSENT;
        }
        if (attendTime.isAfter(lateTime)) {
            return LATE;
        }

        return ATTENDED;
    }

    private static int getDayInfo(int dayOfMonth) {
        return StartTime.findStartTime(dayOfMonth);
    }

    public String getType() {
        return type;
    }
}
