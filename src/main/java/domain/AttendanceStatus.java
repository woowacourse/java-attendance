package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTENDANCE("출석", "정상 등교", 0),
    LATE("지각", "6분 이상 늦음", 5 + 1),
    ABSENT_LATE("결석", "31분 이상 늦음", 30 + 1),
    ABSENT("결석", "출석 기록 없음", Integer.MAX_VALUE),
    ;

    private String name;
    private String description;
    private int elapsedMinutesLimit;

    AttendanceStatus(String name, String description, int elapsedMinutesLimit) {
        this.name = name;
        this.description = description;
        this.elapsedMinutesLimit = elapsedMinutesLimit;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime time) {
        if (!LectureTime.isLectureDate(date)) {
            throw new IllegalArgumentException(date + ": 교육이 없는 날입니다.");
        }

        int elapsedMinutes = LectureTime.calculateElapsedMinutes(date, time);
        return Arrays.stream(values())
                .filter(status -> status.elapsedMinutesLimit <= elapsedMinutes)
                .max(Comparator.comparing(AttendanceStatus::getElapsedMinutesLimit))
                .orElseThrow(() -> new IllegalStateException("논리적으로 발생할 수 없는 예외입니다."));
    }

    public String getName() {
        return name;
    }

    public int getElapsedMinutesLimit() {
        return elapsedMinutesLimit;
    }
}
