package dto.response;

import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;

public record CrewWithPenaltyResponse(
        String name,
        int absentCount,
        int lateCount,
        String penalty
) {
    public static CrewWithPenaltyResponse fromCrew(Crew crew) {
        int absentCount = crew.countAttendanceStatusInDecember(AttendanceStatus.ABSENT);
        int lateCount = crew.countAttendanceStatusInDecember(AttendanceStatus.LATE);

        return new CrewWithPenaltyResponse(
                crew.getName(),
                absentCount,
                lateCount,
                Penalty.findPenaltyMessageByAttendanceStatusCount(lateCount, absentCount)
        );
    }
}
