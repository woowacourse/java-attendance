package attendance.domain;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum AcademicStatus {

    EXPELLED("제적", count -> count > 5),
    INTERVIEW("면담", count -> count >= 3),
    WARNING("경고", count -> count == 2),
    NOT("없음", count -> count < 2);

    private static final int STANDARD_OF_CHANGE_ABSENCE = 3;

    private final String value;
    private final Predicate<Integer> determineStatusConditions;

    AcademicStatus(String value, Predicate<Integer> determineStatusConditions) {
        this.value = value;
        this.determineStatusConditions = determineStatusConditions;
    }

    public String getValue() {
        return value;
    }

    public static String getAcademicStatus(int late, int absent) {
        int totalCount = late / STANDARD_OF_CHANGE_ABSENCE + absent;

        return Stream.of(values())
                .filter(status -> status.determineStatusConditions.test(totalCount))
                .findFirst()
                .orElse(NOT)
                .getValue();
    }
}
