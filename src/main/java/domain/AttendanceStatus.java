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
    ;

    private final String name;
    private final Integer minLatenessMinute;

    AttendanceStatus(String name, Integer minLatenessMinute) {
        this.name = name;
        this.minLatenessMinute = minLatenessMinute;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime attendanceTime) {
        if (attendanceTime == null) {
            return AttendanceStatus.ABSENT;
        }
        LectureTime lectureTime = LectureTime.from(date);
        long difference = MINUTES.between(lectureTime.getStartTime(), attendanceTime);
        return Arrays.stream(values())
            .filter(attendanceStatus -> attendanceStatus.minLatenessMinute < difference)
            .max(Comparator.comparing(AttendanceStatus::getMinLatenessMinute))
            .orElse(null);
    }

    public int getMinLatenessMinute() {
        return minLatenessMinute;
    }

    public String getName() {
        return name;
    }
}
