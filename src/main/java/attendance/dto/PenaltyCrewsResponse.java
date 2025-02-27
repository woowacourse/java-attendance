package attendance.dto;

import attendance.domain.Attendances;
import attendance.domain.AttendancePenalty;
import java.util.List;
import java.util.Map;

public record PenaltyCrewsResponse(
        List<PenaltyCrew> penaltyCrews
) {
    public static PenaltyCrewsResponse from(final Map<String, Attendances> warningCrewsAttendances) {
        return new PenaltyCrewsResponse(
                warningCrewsAttendances.entrySet()
                        .stream()
                        .map(entry -> PenaltyCrew.of(entry.getKey(), entry.getValue()))
                        .toList()
        );
    }

    public record PenaltyCrew(
            String nickname,
            int lateCount,
            int absenceCount,
            AttendancePenalty risk
    ) {
        public static PenaltyCrew of(final String nickname, final Attendances attendances) {
            return new PenaltyCrew(
                    nickname,
                    attendances.countLate(),
                    attendances.countAbsence(),
                    attendances.calculatePenalty()
            );
        }
    }
}
