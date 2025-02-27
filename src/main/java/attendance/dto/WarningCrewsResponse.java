package attendance.dto;

import attendance.domain.Attendances;
import attendance.domain.Warning;
import java.util.List;
import java.util.Map;

public record WarningCrewsResponse(
        List<WarningCrew> warningCrews
) {
    public static WarningCrewsResponse from(final Map<String, Attendances> warningCrewsAttendances) {
        return new WarningCrewsResponse(
                warningCrewsAttendances.entrySet()
                        .stream()
                        .map(entry -> WarningCrew.of(entry.getKey(), entry.getValue()))
                        .toList()
        );
    }

    public record WarningCrew(
            String nickname,
            int lateCount,
            int absenceCount,
            Warning warning
    ) {
        public static WarningCrew of(final String nickname, final Attendances attendances) {
            return new WarningCrew(
                    nickname,
                    attendances.countLate(),
                    attendances.countAbsence(),
                    attendances.calculateWarning()
            );
        }
    }
}
