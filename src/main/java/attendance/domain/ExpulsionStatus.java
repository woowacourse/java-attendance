package attendance.domain;

import java.util.Arrays;

public enum ExpulsionStatus {

    EXPULSION(6),
    INTERVIEW(3),
    WARNING(2),
    NONE(0);

    private final int standard;

    ExpulsionStatus(int standard) {
        this.standard = standard;
    }


    public static ExpulsionStatus findByAbsentCount(int absentCount) {
        return Arrays.stream(values())
                .filter(status -> status.standard <= absentCount)
                .findAny()
                .orElse(NONE);
    }
}
