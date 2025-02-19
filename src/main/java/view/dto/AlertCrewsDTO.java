package view.dto;

import domain.AttendanceAlertLevel;
import java.util.ArrayList;
import java.util.List;

public record AlertCrewsDTO(
        List<AlertCrewDTO> alertCrews
) {
    public static AlertCrewsDTO from(List<AlertCrewDTO> alertCrews) {
        List<AlertCrewDTO> dismissedCrews = alertCrews.stream()
                .filter(crew -> crew.AlertLevel().equals(AttendanceAlertLevel.DISMISSED.getName()))
                .sorted()
                .toList();

        List<AlertCrewDTO> counselCrews = alertCrews.stream()
                .filter(crew -> crew.AlertLevel().equals(AttendanceAlertLevel.COUNSEL_REQUIRED.getName()))
                .sorted()
                .toList();

        List<AlertCrewDTO> cautionCrews = alertCrews.stream()
                .filter(crew -> crew.AlertLevel().equals(AttendanceAlertLevel.CAUTION.getName()))
                .sorted()
                .toList();

        List<AlertCrewDTO> result = new ArrayList<>();
        result.addAll(dismissedCrews);
        result.addAll(counselCrews);
        result.addAll(cautionCrews);
        return new AlertCrewsDTO(result);
    }
}
