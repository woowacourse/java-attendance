package attendance.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public enum WarningLevel {
    REMOVE("제적", count -> count >= 6),
    COUNSELING("면담", count -> count >= 3),
    WARNING("경고", count -> count >= 2),
    NONE("해당 없음", count -> count >= 0);
    private final String level;
    private final Predicate<Integer> predicate;

    WarningLevel(String level, Predicate<Integer> predicate) {
        this.level = level;
        this.predicate = predicate;
    }

    public static WarningLevel of(final Map<AttendanceStatus, Integer> attendanceStatus) {
        int totalAbsence = calculateTotalAbsence(attendanceStatus);
        return Arrays.stream(WarningLevel.values())
                .filter(warningLevel -> warningLevel.predicate.test(totalAbsence))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결석 정보가 올바르지 않습니다."));
    }

    private static int calculateTotalAbsence(final Map<AttendanceStatus, Integer> attendanceStatuses) {
        return attendanceStatuses.get(AttendanceStatus.ABSENCE) + convertLateness(
                attendanceStatuses.get(AttendanceStatus.LATENESS));
    }


    private static int convertLateness(int latenessCount) {
        return latenessCount / 3;
    }

    public String getLevel() {
        return level;
    }
}
