package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record ExpulsionCandidates(
        List<AttendanceStatistics> attendanceStatistics
) {
    public static ExpulsionCandidates from(List<AttendanceStatistics> attendanceStatistics) {
        return new ExpulsionCandidates(attendanceStatistics);
    }

    public ExpulsionCandidates orderByExpulsionRiskLevelAndNickname() {
        List<AttendanceStatistics> sortedList = new ArrayList<>(attendanceStatistics);
        sortedList.sort(Comparator.comparingInt(AttendanceStatistics::getExpulsionRiskLevel).reversed()
                .thenComparing(AttendanceStatistics::nickname));

        return ExpulsionCandidates.from(sortedList);
    }
}
