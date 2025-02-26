package attendance.dto;

import attendance.domain.Attendances;
import java.util.List;

public record AttendancesResponse(
        String nickname,
        List<AttendanceResultResponse> attendances,
        int attendCount,
        int lateCount,
        int absenceCount
) {
    public static AttendancesResponse of(String nickname, Attendances attendances) {
        return new AttendancesResponse(
                nickname,
                attendances.getAttendances()
                        .stream()
                        .map(AttendanceResultResponse::from)
                        .toList(),
                attendances.countAttend(),
                attendances.countLate(),
                attendances.countAbsence()
        );
    }
}
