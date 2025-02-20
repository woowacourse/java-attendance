package domain;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTENDANCE("출석", Integer.MIN_VALUE),
    LATE("지각", 6),
    ABSENT("결석", 31),
    NONE("", Integer.MAX_VALUE),
    ;

    private final String description;
    private final int threshold;

    AttendanceStatus(String description, int threshold) {
        this.description = description;
        this.threshold = threshold;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime attendanceTime) {
        if (attendanceTime == null) {
            return AttendanceStatus.ABSENT;
        }

        LectureTime lectureTime = LectureTime.from(date);
        long difference = MINUTES.between(lectureTime.getStartTime(), attendanceTime);
        return Arrays.stream(values()).filter(attendanceStatus -> attendanceStatus.threshold <= difference)
            .max(Comparator.comparing(AttendanceStatus::getThreshold))
            .orElse(AttendanceStatus.NONE);
    }

    public int getThreshold() {
        return threshold;
    }

    public String getDescription() {
        return description;
    }
}
