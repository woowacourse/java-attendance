package domain;

import java.util.stream.Stream;

public enum AbsencePolicy {

    WARNING(2, "경고"),
    INTERVIEW(3, "면담"),
    DISMISSED(6, "제적"),
    PASS(Integer.MAX_VALUE, "통과");

    private static final int ABSENCE_CONVERSION_RATE = 3;

    private final int lateCount;
    private final String description;

    AbsencePolicy(int lateCount, String description) {
        this.lateCount = lateCount;
        this.description = description;
    }

    public static AbsencePolicy getAbsencePolicy(int absentCount, int lateCount) {
        int totalAbsentCount = calculateTotalAbsentCount(absentCount, lateCount);

        return Stream.of(DISMISSED, INTERVIEW, WARNING)
                .filter(policy -> totalAbsentCount >= policy.lateCount)
                .findFirst()
                .orElse(PASS);
    }

    private static int calculateTotalAbsentCount(final int absentCount, final int lateCount) {
        return absentCount + (lateCount / ABSENCE_CONVERSION_RATE);
    }

    public String getDescription() {
        return description;
    }
}
