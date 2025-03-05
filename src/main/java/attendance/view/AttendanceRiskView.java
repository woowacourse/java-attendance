package attendance.view;

import java.util.Arrays;

public enum AttendanceRiskView {
    WEEDING("제적"),
    INTERVIEW("면담"),
    WARNING("경고");

    private final String name;

    AttendanceRiskView(final String name) {
        this.name = name;
    }

    public static AttendanceRiskView findByName(final String name) {
        return Arrays.stream(values())
                .filter(risk -> risk.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 서버 에러가 발생했습니다."));
    }

    public String getName() {
        return name;
    }
}
