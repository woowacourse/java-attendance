package domain;

import java.util.Arrays;
import java.util.Map;

public record AttendanceStatusCounter(
        Map<AttendanceStatus, Integer> attendanceStatusCounter
) {

    private static final int DEFAULT_COUNT = 0;

    public AttendanceStatusCounter(Map<AttendanceStatus, Integer> attendanceStatusCounter) {
        this.attendanceStatusCounter = attendanceStatusCounter;
        initData();
    }

    public int retrieveAttendanceStatusCount(AttendanceStatus attendanceStatus) {
        return attendanceStatusCounter.get(attendanceStatus);
    }

    public static AttendanceStatusCounter create(Map<AttendanceStatus, Integer> attendanceStatusCounts) {
        return new AttendanceStatusCounter(attendanceStatusCounts);
    }

    private void initData() {
        Arrays.stream(AttendanceStatus.values())
                .forEach(status -> attendanceStatusCounter.putIfAbsent(status, DEFAULT_COUNT));
    }
}
