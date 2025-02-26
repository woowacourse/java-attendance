package service.dto;

import java.util.TreeSet;

public record RiskCrewsResponse(
        TreeSet<RiskCrew> riskCrews
) {

}
