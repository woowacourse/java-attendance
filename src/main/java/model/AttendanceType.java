package model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import model.policy.EducationTimePolicy;

public enum AttendanceType {
    NONE("출석", Duration.ofMinutes(0)),
    LATE("지각", Duration.ofMinutes(5)),
    ABSENT("결석", Duration.ofMinutes(30));

    private final String name;
    private final Duration threshold;

    AttendanceType(final String name, final Duration threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static AttendanceType from(
            final AttendanceDate attendanceDate,
            final AttendanceTime attendanceTime
    ) {
        LocalTime educationStartTime = EducationTimePolicy.getStartTime(attendanceDate.getDayOfWeek());
        Duration duration = attendanceTime.getOverDuration(educationStartTime);
        return Arrays.stream(values())
                .filter(value -> !isWithinThreshold(value, duration))
                .max(Comparator.comparing(value -> value.threshold))
                .orElse(NONE);
    }

    public String getName() {
        return name;
    }

    public Duration getThreshold() {
        return threshold;
    }

    private static boolean isWithinThreshold(final AttendanceType value, final Duration duration) {
        return value.threshold.compareTo(duration) >= 0;
    }
}
