package attendance.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum WarningLevel {
    REMOVE(absenceCount -> absenceCount > 5),
    COUNSELING(absenceCount -> absenceCount >= 3),
    WARNING(absenceCount -> absenceCount >= 2),
    NONE(absenceCount -> absenceCount >= 0);

    private final Predicate<Integer> condition;

    WarningLevel(Predicate<Integer> condition) {
        this.condition = condition;
    }

    public static WarningLevel of(Map<AttendanceStatus, Integer> statusCount){
        int absenceCount = statusCount.get(AttendanceStatus.ABSENCE) + calculateTotalAbsenceCount(statusCount.get(AttendanceStatus.LATENESS));

        return Arrays.stream(values())
                .filter(level -> level.condition.test(absenceCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결석 횟수는 0보다 커야 합니다."));
    }

    public static int calculateTotalAbsenceCount(int latenessCount){
        return latenessCount / 3;
    }
}
