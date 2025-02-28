package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceStatus {
    ATTENDANCE("출석", (elapsedMinutes) -> elapsedMinutes <= 5),
    LATE("지각", (elapsedMinutes) -> 5 < elapsedMinutes && elapsedMinutes <= 30),
    ABSENT("결석", (elapsedMinutes) -> elapsedMinutes > 30),
    ;

    private String description;
    private Function<Integer, Boolean> condition;

    AttendanceStatus(String description, Function<Integer, Boolean> condition) {
        this.description = description;
        this.condition = condition;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime time) {
        if (!LectureTime.isLectureDate(date)) {
            throw new IllegalArgumentException(date + ": 교육이 없는 날입니다.");
        }

        int elapsedMinutes = LectureTime.calculateElapsedMinutes(date, time);
        return Arrays.stream(values())
                .filter(status -> status.condition.apply(elapsedMinutes))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("논리적으로 도달할 수 없는 예외입니다."));
    }

    public String getDescription() {
        return description;
    }
}