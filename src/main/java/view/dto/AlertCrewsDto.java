package view.dto;

import domain.AttendanceAlertLevel;
import java.util.ArrayList;
import java.util.List;

public record AlertCrewsDto(
        List<AlertCrewDto> alertCrews
) {
    public static AlertCrewsDto from(List<AlertCrewDto> alertCrews) {
        List<AlertCrewDto> dismissedCrews = alertCrews.stream()
                .filter(crew -> crew.AlertLevel().equals(AttendanceAlertLevel.DISMISSED.getName()))
                .sorted()
                .toList();

        List<AlertCrewDto> counselCrews = alertCrews.stream()
                .filter(crew -> crew.AlertLevel().equals(AttendanceAlertLevel.COUNSEL_REQUIRED.getName()))
                .sorted()
                .toList();

        List<AlertCrewDto> cautionCrews = alertCrews.stream()
                .filter(crew -> crew.AlertLevel().equals(AttendanceAlertLevel.CAUTION.getName()))
                .sorted()
                .toList();

        List<AlertCrewDto> result = new ArrayList<>();
        result.addAll(dismissedCrews);
        result.addAll(counselCrews);
        result.addAll(cautionCrews);
        return new AlertCrewsDto(result);
    }
}
