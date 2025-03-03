package dto;

import domain.AttendanceStatus;
import domain.Penalty;
import java.util.Map;

public record PenaltyCrew(
        String crewName,
        int absenceCount,
        int lateCount,
        int penaltyCode
) {
    public static PenaltyCrew of(final String crewName, final Map<AttendanceStatus, Integer> attendanceStatuses,
                                 final Penalty penalty) {
        return new PenaltyCrew(
                crewName,
                attendanceStatuses.getOrDefault(AttendanceStatus.ABSENCE, 0),
                attendanceStatuses.getOrDefault(AttendanceStatus.LATE, 0),
                penalty.getCode()
        );
    }
}
