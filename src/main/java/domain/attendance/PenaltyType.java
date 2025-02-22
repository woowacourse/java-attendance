package domain.attendance;

import java.util.Arrays;

public enum PenaltyType {
    BAN(6),
    ONE_ON_ONE(3),
    WARNING(2),
    NONE(0),
    ;

    private final int absenceCount;

    PenaltyType(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    public static PenaltyType getPenaltyType(int absenceCount) {
        return Arrays.stream(PenaltyType.values())
                .filter(type -> type.absenceCount <= absenceCount)
                .findFirst()
                .orElse(PenaltyType.NONE);
    }
}
