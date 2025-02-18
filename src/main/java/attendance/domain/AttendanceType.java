package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceType {
    SAFE("출석"),
    LATE("지각"),
    ABSENT("결석"),
    FREE("자유출근");

    private final String type;

    AttendanceType(String type) {
        this.type = type;
    }

    public static AttendanceType of(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        LocalTime localTime = localDateTime.toLocalTime();
        if (isMonday(dayOfWeek)) {
            return calculateAttendanceType(localTime, 13, 0);
        }
        if (isOtherWeekday(dayOfWeek)) {
            return calculateAttendanceType(localTime, 10, 0);
        }
        return FREE;
    }

    private static AttendanceType calculateAttendanceType(LocalTime localTime, int hour, int minute) {
        if (localTime.isAfter(LocalTime.of(hour, minute + 30))) {
            return ABSENT;
        }
        if (localTime.isAfter(LocalTime.of(hour, minute + 5))) {
            return LATE;
        }
        return SAFE;
    }

    private static boolean isMonday(DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.MONDAY);
    }

    private static boolean isOtherWeekday(DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.TUESDAY) ||
                dayOfWeek.equals(DayOfWeek.WEDNESDAY) ||
                dayOfWeek.equals(DayOfWeek.THURSDAY) ||
                dayOfWeek.equals(DayOfWeek.FRIDAY);
    }

    // 해당 요일의 시작 시각으로부터 5분 초과는 지각으로 간주한다.
    // 해당 요일의 시작 시각으로부터 30분 초과는 결석으로 간주한다.
    // 시간은 월요일은 13:00~18:00
    // 화요일~금요일은 10:00~18:00

}
