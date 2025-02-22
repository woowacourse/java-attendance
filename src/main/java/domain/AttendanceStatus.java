package domain;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum AttendanceStatus {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENCE("결석", 30);

    private final String name;
    private final int boundary;

    AttendanceStatus(final String name, final int boundary) {
        this.name = name;
        this.boundary = boundary;
    }

    public static AttendanceStatus of(final LocalTime time, final int boundaryHour, final int boundaryMinute) {
        final LocalTime timeBoundary = time
                .withHour(boundaryHour)
                .withMinute(boundaryMinute);
        return Arrays.stream(values())
                .sorted((o1, o2) -> o2.boundary - o1.boundary)
                .filter(status -> time.isAfter(timeBoundary.plusMinutes(status.boundary)))
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public static List<AttendanceStatus> sortedStatus() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(AttendanceStatus::getBoundary))
                .toList();
    }

    public String getName() {
        return name;
    }

    public int getBoundary() {
        return boundary;
    }
}
