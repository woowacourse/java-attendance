import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum AttendanceStatus {
    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30),
    ;

    private final int upperBoundMinute;

    AttendanceStatus(int upperBoundMinute) {
        this.upperBoundMinute = upperBoundMinute;
    }

    public static AttendanceStatus from(LocalTime startTime, LocalTime enterTime) {
        List<AttendanceStatus> descendingValues = Arrays.stream(values()).sorted(Comparator.reverseOrder()).toList();
        return descendingValues.stream()
                .filter(status -> enterTime.isAfter(status.getLimitTimeWith(startTime)))
                .findFirst()
                .orElse(ATTENDANCE);
    }

    private LocalTime getLimitTimeWith(LocalTime time) {
        return time.plusMinutes(upperBoundMinute);
    }
}
