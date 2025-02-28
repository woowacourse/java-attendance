package dto;

import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;

public record CrewsWithPenaltyResponse(
        String name,
        int absentCount,
        int lateCount,
        String penalty
) {
    public static CrewsWithPenaltyResponse fromCrew(Crew crew) {
        int absentCount = crew.countAttendanceStatusInDecember(AttendanceStatus.ABSENT);
        int lateCount = crew.countAttendanceStatusInDecember(AttendanceStatus.LATE);

        return new CrewsWithPenaltyResponse(
                crew.getName(),
                absentCount,
                lateCount,
                Penalty.findPenaltyMessageByAttendanceStatusCount(lateCount, absentCount)
        );
    }
}
