package attendance.domain;

import java.time.LocalTime;

public enum CampusTime {
    START(LocalTime.of(8, 0)),
    END(LocalTime.of(23, 0));

    private final LocalTime time;

    CampusTime(final LocalTime time) {
        this.time = time;
    }

    public static void validateOperateTime(final LocalTime time) {
        if (START.time.isBefore(time) || END.time.isAfter(time)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
        }
    }
}
