package model;

import java.time.LocalTime;

public enum CampusOperatingHours {
    OPEN(LocalTime.of(8,0)),
    CLOSE(LocalTime.of(23,0));

    private final LocalTime time;

    CampusOperatingHours(LocalTime time) {
        this.time = time;
    }

    public static void validateOperatingHours(LocalTime time) {
        if (time.isBefore(OPEN.getTime()) || time.isAfter(CLOSE.getTime())) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다. 입력값: " + time);
        }
    }

    public LocalTime getTime() {
        return time;
    }
}
