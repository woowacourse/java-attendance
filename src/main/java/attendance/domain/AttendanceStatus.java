package attendance.domain;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Function;

public enum AttendanceStatus {
    ATTENDANCE((duration -> duration.toMinutes() <= 5)),
    LATE((duration -> 5 < duration.toMinutes() && duration.toMinutes() <= 30)),
    ABSENT((duration -> duration.toMinutes() > 30));

    private final Function<Duration, Boolean> isMatch;

    AttendanceStatus(Function<Duration, Boolean> isMatch) {
        this.isMatch = isMatch;
    }

    public static AttendanceStatus calculate(DayOfWeek dayOfWeek, LocalTime attendanceTime) {
        LocalTime attendanceStartTime = getAttendanceStartTime(dayOfWeek);
        Duration duration = Duration.between(attendanceStartTime, attendanceTime);
        return Arrays.stream(values())
                .filter(attendanceType -> attendanceType.isMatch.apply(duration))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 출석 타입이 없습니다."));
    }

    private static LocalTime getAttendanceStartTime(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return LocalTime.of(13, 0);
        }
        return LocalTime.of(10, 0);
    }
}
