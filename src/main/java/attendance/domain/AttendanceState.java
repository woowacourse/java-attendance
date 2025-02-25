package attendance.domain;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum AttendanceState {
    ABSENCE("결석", 30),
    LATE("지각", 5),
    ATTENDANCE("출석", 0);

    private final String name;
    private final int threshold;

    AttendanceState(final String name, final int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static AttendanceState find(final LocalDateTime dateTime) {
        int overTime = EducationTime.calculateOverTime(dateTime);
        return Arrays.stream(values())
                .filter(type -> type.threshold < overTime)
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public String getName() {
        return name;
    }
}
