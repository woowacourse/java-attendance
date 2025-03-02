package domain.policy;

import java.util.Arrays;

public enum ExpellState {
    EXPELL("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),

    NONE("정상출석", 0);

    private static final int LATE_ABSENT_RATE = 3;

    public final String description;
    private final int limit;

    ExpellState(String description, int limit) {
        this.description = description;
        this.limit = limit;
    }

    public static ExpellState checkExpellStatus(int lateCount, int absentCount) {
        absentCount += lateCount / LATE_ABSENT_RATE;
        final int finalAbsentCount = absentCount;

        return Arrays.stream(ExpellState.values())
                .filter(state -> state.limit <= finalAbsentCount)
                .findFirst()
                .orElse(NONE);
    }
}
