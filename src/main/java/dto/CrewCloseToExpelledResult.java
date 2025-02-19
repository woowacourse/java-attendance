package dto;

import java.util.Map;

import domain.AttendanceStatus;
import domain.Manage;

public record CrewCloseToExpelledResult(
    String nickname,
    Map<AttendanceStatus, Integer> attendanceStatusStatistics,
    Manage manage
) implements Comparable<CrewCloseToExpelledResult> {

    @Override
    public int compareTo(CrewCloseToExpelledResult o) {
        int lateCount = attendanceStatusStatistics.get(AttendanceStatus.LATE);
        int absentCount = attendanceStatusStatistics.get(AttendanceStatus.ABSENT);
        int totalCount = 3 * absentCount + lateCount;
        int opponentLateCount = o.attendanceStatusStatistics().get(AttendanceStatus.LATE);
        int opponentAbsentCount = o.attendanceStatusStatistics().get(AttendanceStatus.ABSENT);
        int opponentTotalCount = 3 * opponentAbsentCount + opponentLateCount;

        if (totalCount == opponentTotalCount) {
            return nickname.compareTo(o.nickname());
        }
        return opponentTotalCount - totalCount;
    }
}
