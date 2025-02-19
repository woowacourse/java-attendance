package domain;

import java.util.Arrays;

public enum PenaltyStatus {
    DISCHARGED(6, "제적"),
    COUNSELING(3, "면담"),
    WARNING(2, "경고"),
    NONE(0, "");


    private final Integer threshold;
    private final String name;

    PenaltyStatus(Integer threshold, String name) {
        this.threshold = threshold;
        this.name = name;
    }

    public static String getStatusName(Integer nonAttendanceCount) {
        return Arrays.stream(values())
                .filter(status -> status.threshold <= nonAttendanceCount)
                .map(status -> status.name)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
