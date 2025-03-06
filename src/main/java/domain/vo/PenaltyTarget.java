package domain.vo;

import domain.CrewPenaltyPolicy;

public record PenaltyTarget(
        String nickName,
        int lateCount,
        int absenceCount,
        CrewPenaltyPolicy crewPenaltyPolicy
) {

    public boolean isNone() {
        return crewPenaltyPolicy.isNone();
    }
}
