package domain.constants;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum ExpulsionStatus {
    NORMAL("정상", 0),
    ADVANCE("경고", 2),
    INTERVIEW("면담", 3),
    EXPULSION("제적", 6);

    private final String name;
    private final int matchTimeMinuteBoundary;

    ExpulsionStatus(final String name, final int matchTimeMinuteBoundary) {
        this.name = name;
        this.matchTimeMinuteBoundary = matchTimeMinuteBoundary;
    }

    public static ExpulsionStatus of(final int absenceCount) {
        return getSortedValuesByTimeBoundary().stream()
                .filter(status -> isOverThanTimeMinute(status, absenceCount))
                .findFirst()
                .orElse(NORMAL);
    }

    private static List<ExpulsionStatus> getSortedValuesByTimeBoundary() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(ExpulsionStatus::getMatchTimeMinuteBoundary).reversed())
                .toList();
    }

    private static boolean isOverThanTimeMinute(final ExpulsionStatus status, int absenceCount) {
        return status.matchTimeMinuteBoundary <= absenceCount;
    }

    public int getMatchTimeMinuteBoundary() {
        return matchTimeMinuteBoundary;
    }

    public String getName() {
        return name;
    }
}
