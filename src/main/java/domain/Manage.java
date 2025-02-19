package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public enum Manage {
    NONE(0, ""),
    WARNING(2, "경고"),
    INTERVIEW(3, "면담"),
    EXPELLED(6, "제적"),
    ;

    private final int absentThreshold;
    private final String description;

    Manage(int absentThreshold, String description) {

        this.absentThreshold = absentThreshold;
        this.description = description;

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

    public String getDescription() {
        return description;
    }
}
