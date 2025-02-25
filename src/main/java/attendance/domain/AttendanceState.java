package attendance.domain;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum AttendanceState {
    ABSENCE(30),
    LATE(5),
    ATTENDANCE(0);

    private final int threshold;

    AttendanceState(final int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceState find(final LocalDateTime dateTime) {
        int overTime = EducationTime.calculateOverTime(dateTime);
        return Arrays.stream(values())
                .filter(type -> type.threshold < overTime)
                .findFirst()
                .orElse(ATTENDANCE);
    }
}
