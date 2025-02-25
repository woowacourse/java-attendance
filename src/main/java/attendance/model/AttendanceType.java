package attendance.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Predicate;

public enum AttendanceType {

    PRESENT(minuteGap -> minuteGap <= 5),
    LATE(minuteGap -> 5 < minuteGap && minuteGap <= 30),
    ABSENT(minuteGap -> 30 < minuteGap),
    ;

    private final Predicate<Integer> condition;

    AttendanceType(Predicate<Integer> condition) {
        this.condition = condition;
    }

    public boolean isMatch(int minuteGap) {
        return condition.test(minuteGap);
    }

    public static AttendanceType determine(LocalTime baseTime, LocalTime attendanceTime) {
        if (attendanceTime == null) {
            return ABSENT;
        }
        return classifyAttendanceType(baseTime, attendanceTime);
    }

    private static AttendanceType classifyAttendanceType(LocalTime baseTime, LocalTime attendanceTime) {
        int minuteGap = (int) Duration.between(baseTime, attendanceTime).toMinutes();
        return Arrays.stream(values())
                .filter(attendanceType -> attendanceType.isMatch(minuteGap))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("출석 유형이 결정되지 않았습니다. 기준 시간을 다시 점검하세요."));
    }
}
