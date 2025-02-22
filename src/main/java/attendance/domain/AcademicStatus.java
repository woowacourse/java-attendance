package attendance.domain;

import java.util.stream.Stream;

public enum AcademicStatus {


    WARNING("경고"),
    INTERVIEW("면담"),
    EXPELLED("제적"),
    NOT("X");

    private static final int STANDARD_OF_CHANGE_ABSENCE = 3;
    private static final int EXPELLED_COUNT = 5;
    private static final int INTERVIEW_COUNT = 3;
    private static final int WARNING_COUNT = 2;


    private final String value;

    AcademicStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getAcademicStatus(int late, int absent) {
        return Stream.of(late / STANDARD_OF_CHANGE_ABSENCE + absent)
                .map(count -> {
                    if (count > EXPELLED_COUNT) {
                        return EXPELLED.getValue();
                    }
                    if (count >= INTERVIEW_COUNT) {
                        return INTERVIEW.getValue();
                    }
                    if (count == WARNING_COUNT) {
                        return WARNING.getValue();
                    }
                    return NOT.getValue();
                })
                .findFirst()
                .orElse(NOT.getValue());
    }
}
