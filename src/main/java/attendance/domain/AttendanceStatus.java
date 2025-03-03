package attendance.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum AttendanceStatus {
    PRESENT("출석", lateMinutes -> lateMinutes <= 5),
    LATE("지각", lateMinutes -> lateMinutes > 5 && lateMinutes <= 30),
    ABSENT("결석", lateMinutes -> lateMinutes > 30);

    private final String title;
    private final Predicate<Long> isLate;

    AttendanceStatus(String title, Predicate<Long> isLate) {
        this.title = title;
        this.isLate = isLate;
    }

    public static AttendanceStatus from(long lateMinutes) {
        return Arrays.stream(AttendanceStatus.values())
                .filter(status -> status.isLate.test(lateMinutes))
                .findFirst()
                .orElse(ABSENT);
    }

    public String getTitle() {
        return title;
    }
}
