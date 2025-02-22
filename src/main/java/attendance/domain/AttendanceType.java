package attendance.domain;

import java.time.LocalTime;

public enum AttendanceType {
    ATTENDANCE("출석", 5),
    LATE("지각", 30),
    ABSENCE("결석", 31);

    private final String name;
    private final int decideValue;

    AttendanceType(String name, int decideValue) {
        this.name = name;
        this.decideValue = decideValue;
    }

    public static AttendanceType decideType(int value) {
        if (ATTENDANCE.decideValue > value) {
            return ATTENDANCE;
        }
        if (LATE.decideValue > value) {
            return LATE;
        }
        return ABSENCE;
    }

    public String getName() {
        return name;
    }
}
