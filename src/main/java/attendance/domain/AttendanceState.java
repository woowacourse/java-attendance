package attendance.domain;

import java.util.Arrays;

public enum AttendanceState {
    ATTENDANCE(5),
    TARDY(30),
    ABSENCE(Integer.MAX_VALUE);

    private final int threshold;

    AttendanceState(final int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceState evaluate(final int overTime) {
        return Arrays.stream(values())
                .filter(state -> state.threshold >= overTime)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 서버 에러가 발생했습니다."));
    }
}
