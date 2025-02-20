package dto;

import java.util.Map;

import domain.AttendanceStatus;
import domain.Manage;

public record CrewCloseToExpelledResult(
    String nickname,
    Map<AttendanceStatus, Integer> attendanceStatusStatistics,
    Manage manage
) {

    public int calculateTotalCount() {
        return attendanceStatusStatistics.get(AttendanceStatus.LATE)
            + attendanceStatusStatistics.get(AttendanceStatus.ABSENT) * 3;
    }
}
