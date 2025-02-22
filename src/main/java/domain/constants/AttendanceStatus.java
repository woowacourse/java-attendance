package domain.constants;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum AttendanceStatus {
    ATTENDANCE("출석", 0),
    LATE("지각", 5),
    ABSENCE("결석", 30);

    private final String name;
    private final int matchTimeMinuteBoundary;

    AttendanceStatus(final String name, final int matchTimeMinuteBoundary) {
        this.name = name;
        this.matchTimeMinuteBoundary = matchTimeMinuteBoundary;
    }

    public static AttendanceStatus of(final LocalTime time, final int boundaryHour, final int boundaryMinute) {
        final LocalTime timeBoundary = time
                .withHour(boundaryHour)
                .withMinute(boundaryMinute);
        return Arrays.stream(values())
                .sorted((o1, o2) -> o2.matchTimeMinuteBoundary - o1.matchTimeMinuteBoundary)
                .filter(status -> time.isAfter(timeBoundary.plusMinutes(status.matchTimeMinuteBoundary)))
                .findFirst()
                .orElse(ATTENDANCE);
    }

    public static List<AttendanceStatus> sortedStatus() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(AttendanceStatus::getMatchTimeMinuteBoundary))
                .toList();
    }

    public String getName() {
        return name;
    }

    public int getMatchTimeMinuteBoundary() {
        return matchTimeMinuteBoundary;
    }
}
