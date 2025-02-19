package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum Manage {
    NONE(0),
    WARNING(2),
    INTERVIEW(3),
    EXPELLED(6),
    ;

    private final int absentThreshold;

    Manage(int absentThreshold) {
        this.absentThreshold = absentThreshold;
    }

    public static Manage of(Map<AttendanceStatus, Integer> attendanceStatusStatistics) {
        int lateCount = attendanceStatusStatistics.getOrDefault(AttendanceStatus.LATE, 0);
        int absentCount = attendanceStatusStatistics.getOrDefault(AttendanceStatus.ABSENT, 0) + lateCount / 3;

        return Arrays.stream(values())
            .filter(manage -> manage.absentThreshold <= absentCount)
            .max(Comparator.comparing(Manage::getAbsentThreshold))
            .orElse(null);
    }

    public int getAbsentThreshold() {
        return absentThreshold;
    }
}
