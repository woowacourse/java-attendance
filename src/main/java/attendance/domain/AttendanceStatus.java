package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public enum AttendanceStatus {

    PRESENCE,
    LATE,
    ABSENCE;

    public static final LocalTime MONDAY_START_TIME = LocalTime.of(13, 0);
    public static final LocalTime WEEKDAY_START_TIME = LocalTime.of(10, 0);
    public static final int ABSENCE_BOUNDARY = 30;
    public static final int LATE_BOUNDARY = 5;

    public static AttendanceStatus findAttendanceStatus(LocalDate attendanceDate, LocalTime attendanceTime) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return getAttendanceStatus(MONDAY_START_TIME, attendanceTime);
        }
        return getAttendanceStatus(WEEKDAY_START_TIME, attendanceTime);
    }

    private static AttendanceStatus getAttendanceStatus(LocalTime weekdayStartTime, LocalTime attendanceTime) {
        if (attendanceTime == null || attendanceTime.isAfter(weekdayStartTime.plusMinutes(ABSENCE_BOUNDARY))) {
            return ABSENCE;
        }
        if (attendanceTime.isAfter(weekdayStartTime.plusMinutes(LATE_BOUNDARY))) {
            return LATE;
        }
        return PRESENCE;
    }

    public static Map<AttendanceStatus, Integer> initMap() {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);

        for (AttendanceStatus status : values()) {
            attendanceStatusCounts.put(status, 0);
        }
        return attendanceStatusCounts;
    }
}
