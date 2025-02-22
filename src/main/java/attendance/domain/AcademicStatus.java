package attendance.domain;

import java.util.stream.Stream;

public enum AcademicStatus {

    WARNING("경고"),
    INTERVIEW("면담"),
    EXPELLED("제적"),
    NOT("X");

    private final String value;

    AcademicStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AcademicStatus getAcademicStatus(int late, int absent) {

        return Stream.of(late / 3 + absent)
                .map(count -> {
                    if (count > 5) {
                        return EXPELLED;
                    }
                    if (count >= 3) {
                        return INTERVIEW;
                    }
                    if (count == 2) {
                        return WARNING;
                    }
                    return NOT;
                })
                .findFirst()
                .orElse(NOT);
    }
}
