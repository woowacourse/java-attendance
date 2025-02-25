package attendance.domain;

import java.util.Arrays;

public enum AttendanceStatus {
    PRESENT("출석", 5),
    LATE("지각", 30),
    ABSENT("결석", Integer.MAX_VALUE);

    private final String title;
    private final long limitTime;

    AttendanceStatus(String title, long limitTime) {
        this.title = title;
        this.limitTime = limitTime;
    }

    public static AttendanceStatus from(long lateTime) {
        return Arrays.stream(AttendanceStatus.values())
                .filter(status -> lateTime <= status.limitTime)
                .findFirst()
                .orElse(ABSENT);
    }

    public String getTitle() {
        return title;
    }
}
