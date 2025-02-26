package attendance.dto;

import attendance.domain.Attendances;
import attendance.domain.Warning;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record WarningCrewsResponse(
    List<WarningCrew> warningCrews
) {
    public static WarningCrewsResponse from(Map<String, Attendances> warningCrewsAttendances) {
        List<WarningCrew> warningCrews = new ArrayList<>();
        for (String nickname : warningCrewsAttendances.keySet()) {
            Attendances attendances = warningCrewsAttendances.get(nickname);
            warningCrews.add(WarningCrew.of(nickname, attendances));
        }
        return new  WarningCrewsResponse(warningCrews);
    }

    public record WarningCrew(
            String nickname,
            int lateCount,
            int absenceCount,
            Warning warning
    ) {
        public static WarningCrew of(String nickname, Attendances attendances) {
            return new WarningCrew(
                    nickname,
                    attendances.countLate(),
                    attendances.countAbsence(),
                    attendances.calculateWarning()
            );
        }
    }
}
