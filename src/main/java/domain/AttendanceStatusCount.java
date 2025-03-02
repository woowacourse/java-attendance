package domain;

import java.util.Map;

public record AttendanceStatusCount(
        int attendanceCount,
        int lateCount,
        int absentCount
) {

    public static AttendanceStatusCount from(Map<AttendanceStatus, Integer> statusCount) {
        return new AttendanceStatusCount(
                statusCount.getOrDefault(AttendanceStatus.ATTENDANCE, 0),
                statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                statusCount.getOrDefault(AttendanceStatus.ABSENT, 0)
        );
    }
}
