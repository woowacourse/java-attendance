package attendance.domain;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum AttendanceState {
    ATTENDANCE(Integer.MIN_VALUE, 5),
    TARDY(6, 30),
    ABSENCE(31, Integer.MAX_VALUE);

    private final int minThreshold;
    private final int maxThreshold;

    AttendanceState(final int minThreshold, final int maxThreshold) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public static AttendanceState evaluate(LocalDateTime dateTime) {
        int overTime = ClassTime.calculateAttendanceDifference(dateTime);
        return Arrays.stream(values())
                .filter(state -> state.minThreshold <= overTime && state.maxThreshold >= overTime)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 서버 에러가 발생했습니다."));
    }
}
