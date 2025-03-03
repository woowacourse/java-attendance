package attendance.dto;

import attendance.domain.AttendancePenalty;
import attendance.domain.PenaltyCrew;

public record PenaltyCrewDto (
    String name,
    int lateCount,
    int absenceCount,
    AttendancePenalty penalty
){
    public static PenaltyCrewDto of(PenaltyCrew penaltyCrew) {
        return new PenaltyCrewDto(
            penaltyCrew.getName(),
            penaltyCrew.getLateCount(),
            penaltyCrew.getAbsenceCount(),
            penaltyCrew.getPenalty());
    }
}
