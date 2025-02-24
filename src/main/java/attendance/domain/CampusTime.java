package attendance.domain;

import java.time.LocalTime;

public enum CampusTime {
    START_TIME(LocalTime.of(8, 0)),
    END_TIME(LocalTime.of(23, 0));

    private final LocalTime time;

    CampusTime(LocalTime time) {
        this.time = time;
    }

    public static void validateOperationTime(LocalTime time) {
        if (time.isBefore(START_TIME.time) || time.isAfter(END_TIME.time)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영시간이 아닙니다.");
        }
    }
}
