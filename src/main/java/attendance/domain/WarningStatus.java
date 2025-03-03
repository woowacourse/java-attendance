package attendance.domain;

import java.util.Arrays;

public enum WarningStatus {
    OUT("제적", 6),
    NEED_MEETING("면담", 3),
    WARNING("경고", 2),
    NONE("해당 없음", 0);

    public static final int LATE_TO_ABSENT_FACTOR = 3;

    private final String title;
    private final int absenceThreshold;

    WarningStatus(String title, int absenceThreshold) {
        this.title = title;
        this.absenceThreshold = absenceThreshold;
    }

    public static WarningStatus from(long absenceCount, long lateCount) {
        long count = absenceCount + lateCount / LATE_TO_ABSENT_FACTOR;
        return Arrays.stream(WarningStatus.values())
                .filter(status -> count >= status.absenceThreshold)
                .findFirst()
                .orElse(NONE);
    }

    public String getTitle() {
        return title;
    }
}
