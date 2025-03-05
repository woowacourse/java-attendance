package domain;

import java.time.LocalTime;

public enum CampusTime {
    OPEN(LocalTime.of(8, 0)),
    CLOSE(LocalTime.of(23, 0));

    private final LocalTime localTime;

    CampusTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public static void validateInTime(final LocalTime time) {
        if (time.isBefore(OPEN.localTime) || time.isAfter(CLOSE.localTime)) {
            throw new IllegalArgumentException("캠퍼스 운영시간이 아닙니다.");
        }
    }
}
