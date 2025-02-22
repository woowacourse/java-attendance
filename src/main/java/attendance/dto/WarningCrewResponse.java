package attendance.dto;

import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.Warning;

public record WarningCrewResponse(
        String nickname,
        long absenceCount,
        long lateCount,
        Warning warning
) {
    public static WarningCrewResponse from(Crew crew) {
        Attendances attendances = crew.getAttendances();
        return new WarningCrewResponse(
                crew.getNickname(),
                attendances.countAbsence(),
                attendances.countLate(),
                attendances.checkWarning()
        );
    }
}
