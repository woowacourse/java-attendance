package attendance.domain.constant;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENCE("결석"),
    HOLIDAY("주말");
    private static final int DEFAULT_HOUR = 0;
    private static final String MONDAY_HOUR_LIMIT = "13";
    private static final String OTHER_DAY_HOUR_LIMIT = "10";
    public static final String LATE_LIMIT = "05";
    public static final String ABSENT_LIMIT = "30";
    private final String name;

    AttendanceStatus(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AttendanceStatus calculateStatus(LocalDateTime localDateTime) {
        if (isDefault(localDateTime)) {
            return ABSENCE;
        }
        if (isHoliday(localDateTime)) {
            return HOLIDAY;
        }
        if (isMonday(localDateTime)) {
            return checkAttendanceStatus(MONDAY_HOUR_LIMIT, localDateTime);
        }
        return checkAttendanceStatus(OTHER_DAY_HOUR_LIMIT, localDateTime);
    }

    private static boolean isMonday(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    private static boolean isDefault(LocalDateTime localDateTime) {
        return localDateTime.getHour() == DEFAULT_HOUR;
    }

    private static boolean isHoliday(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private static AttendanceStatus checkAttendanceStatus(String hourLimit, LocalDateTime localDateTime) {
        int hourMinute = Integer.parseInt(localDateTime.getHour() + addZero(localDateTime.getMinute()));
        int absentTime = Integer.parseInt(hourLimit + AttendanceStatus.ABSENT_LIMIT);
        int lateTime = Integer.parseInt(hourLimit + AttendanceStatus.LATE_LIMIT);
        if (hourMinute > absentTime) {
            return AttendanceStatus.ABSENCE;
        }
        if (hourMinute > lateTime) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    private static String addZero(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }

}
