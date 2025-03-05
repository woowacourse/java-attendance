package attendance.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Predicate;

public enum AttendanceType {

    PRESENT("출석", minuteGap -> minuteGap <= 5),
    LATE("지각", minuteGap -> 5 < minuteGap && minuteGap <= 30),
    ABSENT("결석", minuteGap -> 30 < minuteGap),
    ;

    private final String koreanLabel;
    private final Predicate<Integer> condition;

    AttendanceType(String koreanLabel, Predicate<Integer> condition) {
        this.koreanLabel = koreanLabel;
        this.condition = condition;
    }

    public boolean isMatch(int minuteGap) {
        return condition.test(minuteGap);
    }

    public String getKoreanLabel() {
        return koreanLabel;
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
