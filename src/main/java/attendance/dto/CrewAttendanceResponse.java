package attendance.dto;

import attendance.domain.Attendances;
import attendance.domain.Crew;
import java.util.List;

public record CrewAttendanceResponse(
        String nickname,
        List<AttendanceResultResponse> attendances,
        long absenceCount,
        long lateCount,
        long attendCount
) {
    public static CrewAttendanceResponse from(Crew crew) {
        Attendances attendances = crew.getAttendances();
        List<AttendanceResultResponse> attendanceResponses = crew.getAttendances().getAttendances()
                .stream()
                .map(AttendanceResultResponse::from)
                .toList();
        return new CrewAttendanceResponse(crew.getNickname(), attendanceResponses, attendances.countAbsence(),
                attendances.countLate(), attendances.countAttend());
    }
}
