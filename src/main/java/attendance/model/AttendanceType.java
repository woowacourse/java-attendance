package attendance.model;

import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceType {

    OK(Integer.MIN_VALUE, 5),
    LATE(6, 30),
    ABSENCE(31, Integer.MAX_VALUE),
    ;

    private final int openMinutes;
    private final int closeMinutes;

    AttendanceType(int openMinutes, int closeMinutes) {
        this.openMinutes = openMinutes;
        this.closeMinutes = closeMinutes;
    }

    public static AttendanceType judge(LocalTime startTime, LocalTime attendanceTime) {
        return Arrays.stream(values())
                .filter(attendanceType -> {
                    int difSecond = attendanceTime.toSecondOfDay() - startTime.toSecondOfDay();
                    int difMinutes = difSecond / 60;
                    return attendanceType.contains(difMinutes);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판단할 수 없습니다."));
    }

    public boolean contains(int minutes) {
        return openMinutes <= minutes && minutes <= closeMinutes;
    }
}
