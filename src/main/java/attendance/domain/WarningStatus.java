package attendance.domain;

import java.util.Arrays;

public enum WarningStatus {
    OUT("제적 대상자", 6),
    NEED_MEETING("면담 대상자", 3),
    WARNING("경고 대상자", 2),
    NONE("해당 없음", 0);

    private final String title;
    private final int absenceThreshold;

    WarningStatus(String title, int absenceThreshold) {
        this.title = title;
        this.absenceThreshold = absenceThreshold;
    }

    public static WarningStatus from(long absenceCount, long lateCount) {
        long count = absenceCount + lateCount / 3;
        return Arrays.stream(WarningStatus.values())
                .filter(status -> count >= status.absenceThreshold)
                .findFirst()
                .orElse(NONE);
    }

    public String getTitle() {
        return title;
    }
}
