package type;

import java.util.Arrays;
import java.util.function.Predicate;

public enum PenaltyType {

    ONE_ON_ONE((absenceCount) -> absenceCount >= 3 && absenceCount <= 5),

    BAN((absenceCount) -> absenceCount > 5),

    WARNING((absenceCount) -> absenceCount == 2),
    DEFAULT((absenceCount) -> true);

    private final Predicate<Integer> condition;

    PenaltyType(Predicate<Integer> condition) {
        this.condition = condition;
    }

    public static PenaltyType findByAbsenceCount(int absenceCount) {
        return Arrays.stream(PenaltyType.values())
                .filter(penaltyType -> penaltyType.condition.test(absenceCount))
                .findAny()
                .orElse(PenaltyType.DEFAULT);
    }

    public boolean isAtExpulsionCandidateState() {
        return this != DEFAULT;
    }
}
