package domain;

import java.time.LocalTime;

public record AttendanceTime(
        LocalTime time,
        AttendanceStatus status
) {
    public AttendanceTime {
        if (status == null) {
            throw new IllegalArgumentException("출석 상태는 null일 수 없습니다.");
        }
    }
}
