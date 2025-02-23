package attendance.domain.record;

import java.time.LocalTime;

public enum AttendanceType {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    EXPULSION("결석", 30);

    private final String name;
    private final int overMinutes;

    AttendanceType(String name, int overMinutes) {
        this.name = name;
        this.overMinutes = overMinutes;
    }

    public static AttendanceType parse(LocalTime startTime, LocalTime arriveTime) {
        if (arriveTime.getHour() < startTime.getHour()) {
            return ATTENDANCE;
        }
        int overMinute = arriveTime.getMinute() - startTime.getMinute();
        if (arriveTime.getHour() == startTime.getHour() && overMinute < LATE.overMinutes) {
            return ATTENDANCE;
        }
        if (arriveTime.getHour() == startTime.getHour() && overMinute < EXPULSION.overMinutes) {
            return LATE;
        }
        return EXPULSION;
    }

    public String getName() {
        return name;
    }

    public int getOverMinutes() {
        return overMinutes;
    }
}
