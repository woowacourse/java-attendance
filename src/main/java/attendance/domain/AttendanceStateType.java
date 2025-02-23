package attendance.domain;

import java.util.Arrays;

public enum AttendanceStateType {
    EXPULSION("결석", 30),
    LATE("지각", 5),
    ATTENDANCE("출석", 0);

    private final String name;
    private final int threshold;

    AttendanceStateType(final String name, final int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static AttendanceStateType find(int overTime) {
        return Arrays.stream(AttendanceStateType.values())
                .filter(type -> type.threshold < overTime)
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public String getName() {
        return name;
    }
}
