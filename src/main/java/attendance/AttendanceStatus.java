package attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTENDANCE("출석", null),
    LATENESS("지각", 5),
    ABSENCE("결석", 30),
    ;

    private final String name;
    private final Integer minLateTime;

    AttendanceStatus(String name, Integer minLateTime) {
        this.name = name;
        this.minLateTime = minLateTime;
    }

    public static AttendanceStatus from(LocalDate date, LocalTime time) {
        int lateTime = LectureTime.from(date).getLateTimeOf(time);

        return Arrays.stream(values())
            .filter(status -> status.minLateTime != null)
            .filter(status -> status.minLateTime < lateTime)
            .max(Comparator.comparing(status -> status.minLateTime))
            .orElse(ATTENDANCE);
    }
}
