package domain;

import java.util.Arrays;

public enum PenaltyStatus {
    EXPULSION("제적", 6),
    INTERVIEW("면담", 3),
    CAUTION("경고", 2),
    NONE("비대상자", 0);

    private static final int LATE_TO_ABSENT_RATIO = 3;

    private final String name;
    private final int lowerLimit;

    PenaltyStatus(String name, int lowerLimit) {
        this.name = name;
        this.lowerLimit = lowerLimit;
    }

    public static PenaltyStatus findStatusByNickname(String nickname, Attendances attendances) {
        return Arrays.stream(values())
                .filter(penaltyStatus -> penaltyStatus.lowerLimit <= getTotalAbsentCount(nickname, attendances))
                .findFirst()
                .orElse(NONE);
    }

    public static int getTotalAbsentCount(String nickname, Attendances attendances) {
        int lateCount = attendances.calculateLateCount(nickname);
        int absentCount = attendances.calculateAbsentCount(nickname);
        return absentCount + lateCount / LATE_TO_ABSENT_RATIO;
    }

    public String getName() {
        return name;
    }
}
