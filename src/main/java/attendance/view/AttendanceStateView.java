package attendance.view;

import java.util.Arrays;

public enum AttendanceStateView {
    ABSENCE("결석"),
    LATE("지각"),
    ATTENDANCE("출석");

    private final String name;

    AttendanceStateView(final String name) {
        this.name = name;
    }

    public static AttendanceStateView find(final String name) {
        return Arrays.stream(values())
                .filter(state -> state.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 반환된 출석에 동일한 의미가 없습니다."));
    }

    public String getName() {
        return name;
    }
}
