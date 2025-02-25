package attendance.view;

import java.util.Arrays;

public enum AttendanceRiskView {
    EXPULSION("제적"),
    COUNSELING("면담"),
    WARNING("경고"),
    NONE("해당없음");

    private final String name;

    AttendanceRiskView(final String name) {
        this.name = name;
    }

    public static AttendanceRiskView find(final String name) {
        return Arrays.stream(values())
                .filter(risk -> risk.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 위험도에 일치하는 동일한 의미가 없습니다."));
    }

    public String getName() {
        return name;
    }
}
