package domain;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

public enum Attendance {
    PRESENT("출석", Duration.ofMinutes(5)),
    TARDY("지각", Duration.ofMinutes(30)),
    ABSENT("결석", Duration.ofMinutes(0));

    private static final LocalTime OPEN_HOUR = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_HOUR = LocalTime.of(23, 0);

    private final String name;
    private final Duration thresholdInMinutes;

    Attendance(String name, Duration thresholdInMinutes) {
        this.name = name;
        this.thresholdInMinutes = thresholdInMinutes;
    }

    public static Attendance getAttendanceStatus(Day day, LocalTime time) {
        validateOpenTime(time);
        LocalTime start = day.getStart();
        Duration lateness = Duration.between(start, time);
        return Arrays.stream(Attendance.values())
                .filter(attendance -> attendance.isWithinThreshold(lateness))
                .findFirst()
                .orElse(ABSENT);
    }

    public String getName() {
        return name;
    }

    private static void validateOpenTime(LocalTime time) {
        if (time.isBefore(OPEN_HOUR) || time.isAfter(CLOSE_HOUR)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.\n");
        }
    }

    private boolean isWithinThreshold(Duration duration) {
        return duration.compareTo(thresholdInMinutes) < 0;
    }
}
