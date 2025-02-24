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

    private final int absentMinCount;
    private final String name;

    Manage(int absentMinCount, String name) {
        this.absentMinCount = absentMinCount;
        this.name = name;
    }

    public static Manage of(Map<AttendanceStatus, Integer> attendanceStatusCounter) {
        int lateCount = attendanceStatusCounter.getOrDefault(AttendanceStatus.LATE, 0);
        int absentCount = attendanceStatusCounter.getOrDefault(AttendanceStatus.ABSENT, 0) + lateCount / 3;

        return Arrays.stream(values())
            .filter(manage -> manage.absentMinCount <= absentCount)
            .max(Comparator.comparing(Manage::getAbsentMinCount))
            .orElseThrow(() -> new IllegalStateException("결석 횟수에 맞는 적절한 Manage가 존재하지 않습니다."));
    }

    public int getAbsentMinCount() {
        return absentMinCount;
    }

    public String getName() {
        return name;
    }
}
