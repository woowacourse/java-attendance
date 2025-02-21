package domain;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTENDANCE("출석", Integer.MIN_VALUE),
    LATE("지각", 5),
    ABSENT("결석", 30),
    NONE("쉬는 날", Integer.MAX_VALUE),
    ;

    private final String description;
    private final int elapsedMinutesLimit;

    AttendanceStatus(String description, int elapsedMinutesLimit) {
        this.description = description;
        this.elapsedMinutesLimit = elapsedMinutesLimit;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime attendanceTime) {
        if (attendanceTime == null) {
            return AttendanceStatus.ABSENT;
        }

        LectureTime lectureTime = LectureTime.from(date);
        long elapsedMinutes = MINUTES.between(lectureTime.getStartTime(), attendanceTime);
        return Arrays.stream(values())
                .filter(attendanceStatus -> attendanceStatus.elapsedMinutesLimit < elapsedMinutes)
                .max(Comparator.comparing(AttendanceStatus::getElapsedMinutesLimit))
                .orElse(AttendanceStatus.NONE);
    }

    public int getElapsedMinutesLimit() {
        return elapsedMinutesLimit;
    }

    public String getDescription() {
        return description;
    }
}
