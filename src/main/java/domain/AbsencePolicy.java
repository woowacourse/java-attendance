package domain;

import java.util.stream.Stream;

public enum AbsencePolicy {

    WARNING(2, "경고"),
    INTERVIEW(3, "면담"),
    DISMISSED(6, "제적"),
    PASS(Integer.MAX_VALUE, "통과");

    private final int value;
    private final String description;

    AbsencePolicy(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public static String getAbsencePolicy(int absentCount, int lateCount) { //지각, 결석
        int totalAbsentCount = absentCount + (lateCount / 3);

        return Stream.of(DISMISSED, INTERVIEW, WARNING)
                .filter(policy -> totalAbsentCount >= policy.value)
                .findFirst()
                .map(policy -> policy.description)
                .orElse(PASS.description);
    }
}
