package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendanceStatus {
    ATTENDANCE("출석", "정상 등교", Integer.MIN_VALUE),
    LATE("지각", "5분 초과", 5),
    ABSENT_LATE("결석", "30분 초과", 30),
    ABSENT("결석", "출석 기록 없음", Integer.MAX_VALUE),
    ;

    private String title;
    private String description;
    private int elapsedMinutesLimit;

    AttendanceStatus(String title, String description, int elapsedMinutesLimit) {
        this.title = title;
        this.description = description;
        this.elapsedMinutesLimit = elapsedMinutesLimit;
    }

    // TODO: 날짜와 시간으로 출석 상태를 계산한다
    public static AttendanceStatus of(LocalDate date, LocalTime time) {
        return ATTENDANCE;
    }
}
