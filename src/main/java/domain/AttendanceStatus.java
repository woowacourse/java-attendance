package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    PRESENT("출석"),
    LATE("지각"),
    ABSENT("결석");

    public static final int PRESENT_LIMIT_TIME = 5;
    public static final int LATE_LIMIT_TIME = 30;
    public static final int MONDAY = 1;
    public static final int MONDAY_STANDARD_HOUR = 13;
    public static final int OTHER_DAY_STANDARD_HOUR = 10;

    final String korean;

    AttendanceStatus(String korean) {
        this.korean = korean;
    }

    public static AttendanceStatus calculateAttendanceStatus(LocalDateTime dateTime) {
        LocalTime standardTime = getStandardTime(dateTime);
        LocalTime attendanceTime = LocalTime.of(dateTime.getHour(), dateTime.getMinute());

        long timeDifference = Duration.between(standardTime, attendanceTime).toMinutes();
        if (timeDifference <= PRESENT_LIMIT_TIME) {
            return PRESENT;
        }
        if (timeDifference <= LATE_LIMIT_TIME) {
            return LATE;
        }
        return ABSENT;
    }

    private static LocalTime getStandardTime(LocalDateTime dateTime) {
        if (dateTime.getDayOfWeek().getValue() == MONDAY) {
            return LocalTime.of(MONDAY_STANDARD_HOUR, 0);
        }
        return LocalTime.of(OTHER_DAY_STANDARD_HOUR, 0);
    }

    public String getKorean() {
        return korean;
    }
}
