package attendance.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum WarningLevel {
    REMOVE("제적", absenceCount -> absenceCount > 5),
    COUNSELING("면담", absenceCount -> absenceCount >= 3),
    WARNING("경고", absenceCount -> absenceCount >= 2),
    NONE("해당 없음", absenceCount -> absenceCount >= 0);

    private final String description;
    private final Predicate<Integer> condition;

    WarningLevel(String description, Predicate<Integer> condition) {
        this.description = description;
        this.condition = condition;
    }

    public static WarningLevel from(Map<AttendanceStatus, Integer> statusCount) {
        int absenceCount = statusCount.get(AttendanceStatus.ABSENCE) + calculateTotalAbsenceCount(
                statusCount.get(AttendanceStatus.LATENESS));

        return Arrays.stream(values())
                .filter(level -> level.condition.test(absenceCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결석 횟수는 0보다 커야 합니다."));
    }

    public static int calculateTotalAbsenceCount(int latenessCount) {
        return latenessCount / 3;
    }

    public String getDescription() {
        return description;
    }
}
