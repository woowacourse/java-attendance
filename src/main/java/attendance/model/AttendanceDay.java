package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

public enum AttendanceDay {

    MON(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
    TUE(DayOfWeek.TUESDAY, LocalTime.of(10, 0)),
    WED(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0)),
    THR(DayOfWeek.THURSDAY, LocalTime.of(10, 0)),
    FRI(DayOfWeek.FRIDAY, LocalTime.of(10, 0)),
    ;

    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;

    AttendanceDay(DayOfWeek dayOfWeek, LocalTime startTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public static LocalTime findStartTime(DayOfWeek dayOfWeek) {
        return Arrays.stream(values())
                .filter(startTime -> startTime.isSameDayOfWeek(dayOfWeek))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 요일의 출석 시작 시간을 찾을 수 없습니다."))
                .startTime;
    }

    private boolean isSameDayOfWeek(DayOfWeek dayOfWeek) {
        return this.dayOfWeek == dayOfWeek;
    }
}
