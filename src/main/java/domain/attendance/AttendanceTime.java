package domain.attendance;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum AttendanceTime {
    MON(13, 5, 1),
    TUE(10, 5, 2),
    WED(10, 5, 3),
    THU(10, 5, 4),
    FRI(10, 5, 5),
    ;

    private final int hour;
    private final int minute;
    private final int dayOfWeek;

    AttendanceTime(int hour, int minute, int dayOfWeek) {
        if (hour < 8 || hour > 22) {
            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석이 가능합니다.");
        }

        this.hour = hour;
        this.minute = minute;
        this.dayOfWeek = dayOfWeek;
    }

    public static boolean isAttendance(int dayOfWeek, LocalDateTime dateTime) {
        return Arrays.stream(values())
                .filter(value -> value.dayOfWeek == dayOfWeek)
                .anyMatch(value -> value.hour > dateTime.getHour() || (value.hour == dateTime.getHour()
                        && value.minute >= dateTime.getMinute()));
    }

    public static boolean isAbsence(int dayOfWeek, LocalDateTime dateTime) {
        return Arrays.stream(values())
                .filter(value -> value.dayOfWeek == dayOfWeek)
                .anyMatch(value ->value.hour < dateTime.getHour() || (value.hour == dateTime.getHour()
                        && value.minute + 25 < dateTime.getMinute()));
    }
}
