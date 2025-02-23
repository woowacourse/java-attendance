package attendance.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceType {

    OK("출석", difMinutes -> difMinutes <= 5),
    LATE("지각", difMinutes -> difMinutes > 5 && difMinutes <= 30),
    ABSENCE("결석", difMinutes -> difMinutes > 30),
    ;

    private static final int MINUTE_SCALE = 60;

    private final String label;
    private final Function<Integer, Boolean> isMatch;

    AttendanceType(String label, Function<Integer, Boolean> isMatch) {
        this.label = label;
        this.isMatch = isMatch;
    }

    public static AttendanceType judge(LocalTime startTime, LocalTime attendanceTime) {
        return Arrays.stream(values())
                .filter(attendanceType -> attendanceType.isMatch.apply(calculateDifMinutes(startTime, attendanceTime)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판단할 수 없습니다."));
    }

    private static int calculateDifMinutes(LocalTime startTime, LocalTime attendanceTime) {
        int difSecond = attendanceTime.toSecondOfDay() - startTime.toSecondOfDay();
        return difSecond / MINUTE_SCALE;
    }

    public String getLabel() {
        return label;
    }
}
