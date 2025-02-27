package view.dto;

import domain.CrewDismiss;
import domain.CrewDismissHistory;

public record CrewDismissHistoryDto(String crewNickname, int absence, int late, String attendanceStatus) {
    public static CrewDismissHistoryDto from(CrewDismissHistory crewDismissHistory) {
        String crewNickname = crewDismissHistory.crewNickname();
        CrewDismiss crewDismiss = crewDismissHistory.crewDismiss();
        return new CrewDismissHistoryDto(crewNickname, crewDismiss.absence(), crewDismiss.late(),
                crewDismiss.dismissStatusMessage());
    }
}
