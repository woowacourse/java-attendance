package domain;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTENDANCE("출석", "출석 시간 준수", Integer.MIN_VALUE),
    LATE("지각", "출석 시간 5분 초과해서 지각", 5),
    ABSENT_LATE("결석", "출석 시간 30분 초과해서 결석", 30),
    ABSENT("결석", "출석 기록을 하지 않아서 결석", Integer.MAX_VALUE),
    OFF_DAY("쉬는 날", "출석 상태를 정의할 수 없음", Integer.MAX_VALUE),
    ;

    private final String title;
    private final String description;
    private final int elapsedMinutesLimit;

    AttendanceStatus(String title, String description, int elapsedMinutesLimit) {
        this.title = title;
        this.description = description;
        this.elapsedMinutesLimit = elapsedMinutesLimit;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime attendanceTime) {
        LectureTime lectureTime = LectureTime.from(date);
        long elapsedMinutes = MINUTES.between(lectureTime.getStartTime(), attendanceTime);
        return Arrays.stream(values())
                .filter(attendanceStatus -> attendanceStatus.elapsedMinutesLimit < elapsedMinutes)
                .max(Comparator.comparing(AttendanceStatus::getElapsedMinutesLimit))
                .orElse(AttendanceStatus.OFF_DAY);
    }

    public int getElapsedMinutesLimit() {
        return elapsedMinutesLimit;
    }

    public String getTitle() {
        return title;
    }
}
