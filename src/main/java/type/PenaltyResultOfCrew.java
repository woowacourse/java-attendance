package type;

import crew.Crew;

public record PenaltyResultOfCrew(
        Crew crew,
        AttendanceTypeCount attendanceTypeCount,
        PenaltyType penaltyType
) {
    public static PenaltyResultOfCrew from(Crew crew, AttendanceTypeCount attendanceTypeCount) {
        return new PenaltyResultOfCrew(crew, attendanceTypeCount, PenaltyType.findByAbsenceCount(
                attendanceTypeCount.getAdjustedAbsenceCount()));
    }
}

