package domain;

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

    // TODO: PenaltyType sort 하기, 혹은 Enum 상수 선언 순서에 영향받지 않는 더 나은 방법 고려
    public static PenaltyType getPenaltyType(int absenceCount) {
        return Arrays.stream(PenaltyType.values())
                .filter(type -> type.absenceCount <= absenceCount)
                .findFirst()
                .orElse(PenaltyType.NONE);
    }
}
