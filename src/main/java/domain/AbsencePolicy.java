package domain;

import java.util.stream.Stream;

public enum AbsencePolicy {

    WARNING(2, "경고"),
    INTERVIEW(3, "면담"),
    DISMISSED(6, "제적"),
    PASS(Integer.MAX_VALUE, "대상자 아님");

    private final int value;
    private final String description;

    AbsencePolicy(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static AbsencePolicy getAbsencePolicy(int absentCount, int lateCount) {
        int totalAbsentCount = absentCount + (lateCount / 3);

        return Stream.of(DISMISSED, INTERVIEW, WARNING)
                .filter(policy -> totalAbsentCount >= policy.value)
                .findFirst()
                .orElse(PASS);
    }
}
