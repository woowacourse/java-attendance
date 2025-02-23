package dto;

import java.util.Map;

import domain.AttendanceStatus;
import domain.Manage;

public record CrewAlmostExpelledResult(
    String nickname,
    Map<AttendanceStatus, Integer> attendanceStatusStatistics,
    Manage manage
) {

    public static final int ABSENT_WEIGHT = 3;

    public int calculateTotalCount() {
        return attendanceStatusStatistics.get(AttendanceStatus.LATE)
            + attendanceStatusStatistics.get(AttendanceStatus.ABSENT) * ABSENT_WEIGHT;
    }
}
