package dto;

import domain.AttendanceStatus;
import java.util.List;
import java.util.Map;

public record AttendanceHistory(
        List<AttendanceDetails> attendanceDetails,
        int attendanceCount,
        int lateCount,
        int absenceCount,
        int penaltyCode
) {
    public static AttendanceHistory of(final List<AttendanceDetails> attendanceDetails, final Map<AttendanceStatus, Integer> attendanceStatuses, final int penaltyCode) {
        return new AttendanceHistory(
                attendanceDetails,
                attendanceStatuses.getOrDefault(AttendanceStatus.ATTENDANCE, 0),
                attendanceStatuses.getOrDefault(AttendanceStatus.LATE, 0),
                attendanceStatuses.getOrDefault(AttendanceStatus.ABSENCE, 0),
                penaltyCode
        );
    }
}
