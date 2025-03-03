package domain;

import java.util.Arrays;

public enum AttendanceStatus {
    EXPULSION("제적", 6),
    INTERVIEW("면담", 3),
    WARNING("경고", 2),
    NORMAL("정상", 0);

    private final String status;
    private final int count;

    AttendanceStatus(String status, int count) {
        this.status = status;
        this.count = count;
    }

    public static AttendanceStatus findAttendanceStatus(final int lateCount, final int absenceCount) {
        int totalCount = absenceCount + (lateCount / 3);

        return Arrays.stream(AttendanceStatus.values())
                .sorted((a, b) -> Integer.compare(b.count, a.count))
                .filter(status -> totalCount >= status.count)
                .findFirst()
                .orElse(NORMAL);
    }

    public String getStatus() {
        return status;
    }
}
