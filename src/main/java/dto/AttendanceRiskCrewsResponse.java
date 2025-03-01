package dto;

import java.util.EnumMap;
import java.util.List;
import model.AttendanceType;
import model.Crew;
import model.PunishmentType;

public record AttendanceRiskCrewsResponse(
        List<AttendanceRiskCrewResponse> riskCrewResponses
) {

    public record AttendanceRiskCrewResponse(
            Crew crew,
            EnumMap<AttendanceType, Integer> attendanceTotal,
            PunishmentType punishmentType
    ) {
    }
}
