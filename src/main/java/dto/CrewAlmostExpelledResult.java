package dto;

import domain.AttendanceStatus;
import domain.AttendanceStatusStatistics;
import domain.Manage;

public record CrewAlmostExpelledResult(
        String nickname,
        AttendanceStatusStatistics attendanceStatusStatistics,
        Manage manage
) implements Comparable<CrewAlmostExpelledResult> {

    @Override
    public int compareTo(CrewAlmostExpelledResult o) {
        int totalCount = calculateTotalAbsentCount();
        int opponentTotalCount = o.calculateTotalAbsentCount();
        if (totalCount == opponentTotalCount) {
            return nickname.compareTo(o.nickname);
        }
        return opponentTotalCount - totalCount;
    }

    public int calculateTotalAbsentCount() {
        return attendanceStatusStatistics.getStatusCounter().get(AttendanceStatus.LATE)
                + attendanceStatusStatistics.getStatusCounter().get(AttendanceStatus.ABSENT_LATE) * 3;
    }
}
