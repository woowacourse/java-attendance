package attendance.domain;

import java.util.function.Predicate;
import java.util.stream.Stream;

public enum AcademicStatus {

    EXPELLED("제적", count -> count > 5),
    INTERVIEW("면담", count -> count >= 3),
    WARNING("경고", count -> count == 2),
    NOT("없음", count -> count < 2);

    private final String status;
    private final Predicate<Integer> determineStatusConditions;

    AcademicStatus(String status, Predicate<Integer> determineStatusConditions) {
        this.status = status;
        this.determineStatusConditions = determineStatusConditions;
    }

    public static AcademicStatus getStatus(int late, int absent) {
        int totalCount = late / 3 + absent;

        return Stream.of(values())
                .filter(status -> status.determineStatusConditions.test(totalCount))
                .findFirst()
                .orElse(NOT);
    }
}
