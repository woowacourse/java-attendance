package attendance.dto;

import attendance.domain.AttendancePenalty;
import attendance.domain.Attendances;
import java.util.List;
import java.util.Map;

public record PenaltyCrewsResponse(
        List<PenaltyCrewResponse> penaltyCrewResponses
) {
    public static PenaltyCrewsResponse from(final Map<String, Attendances> warningCrewsAttendances) {
        return new PenaltyCrewsResponse(
                warningCrewsAttendances.entrySet()
                        .stream()
                        .map(entry -> PenaltyCrewResponse.of(entry.getKey(), entry.getValue()))
                        .toList()
        );
    }

    public record PenaltyCrewResponse(
            String nickname,
            int lateCount,
            int absenceCount,
            AttendancePenalty risk
    ) {
        public static PenaltyCrewResponse of(final String nickname, final Attendances attendances) {
            return new PenaltyCrewResponse(
                    nickname,
                    attendances.countLate(),
                    attendances.countAbsence(),
                    attendances.calculatePenalty()
            );
        }
    }
}
