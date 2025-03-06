package domain.policy.attend.time;

import java.time.LocalTime;

public enum CampusTimeRule {

    OPEN(8, 0, "캠퍼스 운영 시작 시간"),
    CLOSE(23, 0, "캠퍼스 운영 끝 시간");

    private final int hour;
    private final int minute;
    private final String description;

    CampusTimeRule(int hour, int minute, String description) {
        this.hour = hour;
        this.minute = minute;
        this.description = description;
    }

    public static boolean canAttendTime(LocalTime time) {
        return !time.isBefore(OPEN.toLocalTime()) && !time.isAfter(CLOSE.toLocalTime());
    }

    private LocalTime toLocalTime() {
        return LocalTime.of(hour, minute);
    }

    public String getDescription() {
        return description;
    }
}
