package attendance.view;

import java.util.Arrays;

public enum AttendanceStateView {
    ATTENDANCE("출석"),
    TARDY("지각"),
    ABSENCE("결석");

    private final String name;

    AttendanceStateView(final String name) {
        this.name = name;
    }

    public static AttendanceStateView findByName(final String name) {
        return Arrays.stream(values())
                .filter(state -> state.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 서버 에러가 발생했습니다."));
    }

    public String getName() {
        return name;
    }
}
