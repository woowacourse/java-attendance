import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTENDANCE(0),
    LATE(5),
    ABSENT(30),
    ;

    private final int threshold;

    AttendanceStatus(int threshold) {
        this.threshold = threshold;
    }

    public static AttendanceStatus of(LocalDate date, LocalTime attendanceTime) {
        if(attendanceTime == null) {
            return AttendanceStatus.ABSENT;
        }

        LectureTime lectureTime = LectureTime.from(date);
        long difference = MINUTES.between(lectureTime.getStartTime(), attendanceTime);
        return Arrays.stream(values()).filter(attendanceStatus -> attendanceStatus.threshold < difference)
            .max(Comparator.comparing(AttendanceStatus::getThreshold))
            .orElse(null);
    }

    public int getThreshold() {
        return threshold;
    }
}
