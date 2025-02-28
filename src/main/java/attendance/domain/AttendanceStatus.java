package attendance.domain;

import java.util.Arrays;

public enum AttendanceStatus {
    PRESENT("출석", 5),
    LATE("지각", 30),
    ABSENT("결석", Integer.MAX_VALUE);

    private final String title;
    private final long limitMinutes;

    AttendanceStatus(String title, long limitMinutes) {
        this.title = title;
        this.limitMinutes = limitMinutes;
    }

    public static AttendanceStatus from(long lateMinutes) {
        return Arrays.stream(AttendanceStatus.values())
                .filter(status -> lateMinutes <= status.limitMinutes)
                .findFirst()
                .orElse(ABSENT);
    }

    public String getTitle() {
        return title;
    }
}
