package attendance.domain;

import java.util.Arrays;
import java.util.Map;

import static attendance.domain.AttendanceStatusChecker.*;

public enum ExpulsionStatus {
    EXPULSION(6),
    INTERVIEW(3),
    WARNING(2),
    NONE(0);

    private final int absentStandard;

    ExpulsionStatus(int absentStandard) {
        this.absentStandard = absentStandard;
    }

    public static ExpulsionStatus from(long absentCount) {
        return Arrays.stream(values())
                .filter(expulsionStatus -> expulsionStatus.absentStandard <= absentCount)
                .findAny()
                .orElse(NONE);
    }
}
