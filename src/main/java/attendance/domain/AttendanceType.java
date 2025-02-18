package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {
    SAFE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    FREE("자유출근");

    private final String type;

    AttendanceType(String type) {
        this.type = type;
    }
}
