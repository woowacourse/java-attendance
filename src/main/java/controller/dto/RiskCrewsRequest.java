package controller.dto;

import java.time.LocalDate;

public record RiskCrewsRequest(
        LocalDate today
) {

}
