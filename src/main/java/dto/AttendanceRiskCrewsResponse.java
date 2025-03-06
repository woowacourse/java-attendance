package dto;

import java.util.List;
import java.util.Map;
import model.AttendanceType;
import model.Crew;
import model.PunishmentType;

public record AttendanceRiskCrewsResponse(
        List<AttendanceRiskCrewResponse> riskCrewResponses
) {

    public record AttendanceRiskCrewResponse(
            Crew crew,
            Map<AttendanceType, Integer> attendanceTotal,
            PunishmentType punishmentType
    ) {
    }
}
