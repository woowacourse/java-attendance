package attendance.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceType {

    OK((difMinutes) -> difMinutes <= 5),
    LATE((difMinutes) -> difMinutes > 5 && difMinutes <= 30),
    ABSENCE((difMinutes) -> difMinutes > 30),
    ;

    private static final int MINUTE_SCALE = 60;

    private final Function<Integer, Boolean> isMatch;

    AttendanceType(Function<Integer, Boolean> isMatch) {
        this.isMatch = isMatch;
    }

    public static AttendanceType judge(LocalTime startTime, LocalTime attendanceTime) {
        if (attendanceTime == null) {
            return AttendanceType.ABSENCE;
        }

        return Arrays.stream(values())
                .filter(attendanceType -> attendanceType.isMatch.apply(calculateDifMinutes(startTime, attendanceTime)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("판단할 수 없습니다."));
    }

    private static int calculateDifMinutes(LocalTime startTime, LocalTime attendanceTime) {
        int difSecond = attendanceTime.toSecondOfDay() - startTime.toSecondOfDay();
        return difSecond / MINUTE_SCALE;
    }
}
