package domain.attendance;

import java.util.Arrays;
import java.util.function.Predicate;

public enum PenaltyType {
    BAN(absenceCount -> absenceCount > 5),
    ONE_ON_ONE(absenceCount -> absenceCount >= 3),
    WARNING(absenceCount -> absenceCount >= 2),
    NONE(absenceCount -> absenceCount < 2),
    ;

    private final Predicate<Integer> condition;

    PenaltyType(Predicate<Integer> condition) {
        this.condition = condition;
    }

    public static PenaltyType getPenaltyType(int absenceCount) {
        return Arrays.stream(PenaltyType.values())
                .filter(type -> type.condition.test(absenceCount))
                .findFirst()
                .orElse(PenaltyType.NONE);
    }
}
