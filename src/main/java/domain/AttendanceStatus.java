package domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public enum AttendanceStatus {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENCE("결석", 30);

    String name;
    int boundary;

    AttendanceStatus(final String name, final int boundary) {
        this.name = name;
        this.boundary = boundary;
    }

    public static AttendanceStatus of(final LocalDateTime dateTime) {
        return Arrays.stream(values())
                .sorted((o1, o2) -> o2.boundary - o1.boundary)
                .filter(status -> dateTime.isAfter(ClassTime.calculateBoundaryTime(dateTime.toLocalDate()).plusMinutes(status.boundary)))
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public static List<AttendanceStatus> sortedStatus() {
        return Arrays.stream(values())
                .sorted((o1, o2) -> o1.boundary - o2.boundary)
                .toList();
    }

    public String getName() {
        return name;
    }
}
