package domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {

    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30);

    private static final LocalTime MONDAY_START_CAMPUS = LocalTime.of(13, 0);
    private static final LocalTime BESIDE_MONDAY_START_CAMPUS = LocalTime.of(10, 0);
    private final int boundaryMinute;

    AttendanceStatus(final int boundaryMinute) {
        this.boundaryMinute = boundaryMinute;
    }

    public static AttendanceStatus calculateStatus(final LocalTime time, final DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(AttendanceStatus::getBoundaryMinute).reversed())
                .filter(status -> {
                    if (dayOfWeek == DayOfWeek.MONDAY) {
                        return time.isAfter(MONDAY_START_CAMPUS.plusMinutes(status.boundaryMinute));
                    }
                    return time.isAfter(BESIDE_MONDAY_START_CAMPUS.plusMinutes(status.boundaryMinute));
                })
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public int getBoundaryMinute() {
        return boundaryMinute;
    }
}
