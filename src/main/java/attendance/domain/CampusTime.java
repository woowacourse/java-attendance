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
        if (START.time.isAfter(time) || END.time.isBefore(time)) {
            throw new IllegalArgumentException(formatErrorMessage());
        }
    }

    private static String formatErrorMessage() {
        return String.format("[ERROR] 캠퍼스 운영 시간은 %s ~ %s 입니다.",
                START.time,
                END.time
        );
    }
}
