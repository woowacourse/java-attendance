package domain.attendance;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
        AttendanceTime attendanceTime = attendanceTimeMap.get(dayOfWeek);
        if (attendanceTime == null) {
            return false;
        }
        return attendanceTime.hour > dateTime.getHour() ||
                (attendanceTime.hour == dateTime.getHour() && attendanceTime.minute >= dateTime.getMinute());
    }

    public static boolean isAbsence(int dayOfWeek, LocalDateTime dateTime) {
        AttendanceTime attendanceTime = attendanceTimeMap.get(dayOfWeek);
        if (attendanceTime == null) {
            return false;
        }
        return attendanceTime.hour < dateTime.getHour() || (attendanceTime.hour == dateTime.getHour()
                && attendanceTime.minute + 25 < dateTime.getMinute());
    }

    private static final Map<Integer, AttendanceTime> attendanceTimeMap = Arrays.stream(values())
            .collect(Collectors.toMap(attendance -> attendance.dayOfWeek, attendance -> attendance));
}
