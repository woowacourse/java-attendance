package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum AttendanceTime {
    MON(13, 5, DayOfWeek.MONDAY),
    TUE(10, 5, DayOfWeek.TUESDAY),
    WED(10, 5, DayOfWeek.WEDNESDAY),
    THU(10, 5, DayOfWeek.THURSDAY),
    FRI(10, 5, DayOfWeek.FRIDAY),
    ;

    private final int hour;
    private final int minute;
    private final DayOfWeek dayOfWeek;

    AttendanceTime(int hour, int minute, DayOfWeek dayOfWeek) {
        if (hour < 8 || hour > 22) {
            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
        this.hour = hour;
        this.minute = minute;
        this.dayOfWeek = dayOfWeek;
    }

    public static boolean isAttendance(DayOfWeek dayOfWeek, LocalDateTime dateTime) {
        AttendanceTime attendanceTime = attendanceTimeMap.get(dayOfWeek);
        if (attendanceTime == null) {
            return false;
        }
        return attendanceTime.hour > dateTime.getHour() ||
                (attendanceTime.hour == dateTime.getHour() && attendanceTime.minute >= dateTime.getMinute());
    }

    public static boolean isAbsence(DayOfWeek dayOfWeek, LocalDateTime dateTime) {
        AttendanceTime attendanceTime = attendanceTimeMap.get(dayOfWeek);
        if (attendanceTime == null) {
            return false;
        }
        return attendanceTime.hour < dateTime.getHour() || (attendanceTime.hour == dateTime.getHour()
                && attendanceTime.minute + 25 < dateTime.getMinute());
    }

    private static final Map<DayOfWeek, AttendanceTime> attendanceTimeMap = Arrays.stream(values())
            .collect(Collectors.toMap(attendance -> attendance.dayOfWeek, attendance -> attendance));
}
