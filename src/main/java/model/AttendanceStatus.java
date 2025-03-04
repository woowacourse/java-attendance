package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    TARDINESS("지각"),
    ABSENCE("결석");

    private static final int TARDINESS_OVER_LIMIT = 5;
    private static final int ABSENCE_OVER_LIMIT = 30;

    private final String displayName;

    AttendanceStatus(final String displayName) {
        this.displayName = displayName;
    }

    public static AttendanceStatus findByAttendanceDateTime(final AttendanceDateTime attendanceDateTime) {
        if (BusinessHours.notWithInOperatingTime(attendanceDateTime.getAttendanceTime())) {
            return ABSENCE;
        }
        final BusinessHours businessHours = BusinessHours.find(attendanceDateTime);
        final AttendanceTime startTime = businessHours.getStartTime();

        return findStatus(attendanceDateTime, startTime);
    }

    private static AttendanceStatus findStatus(final AttendanceDateTime attendanceDateTime, final AttendanceTime startTime) {
        if (startTime.isBeforeToPlus(attendanceDateTime, ABSENCE_OVER_LIMIT)) {
            return ABSENCE;
        }

        if (startTime.isBeforeToPlus(attendanceDateTime, TARDINESS_OVER_LIMIT)) {
            return TARDINESS;
        }
        return ATTENDANCE;
    }

    public static Map<AttendanceStatus, Integer> countStatus(final List<AttendanceStatus> attendanceStatuses) {
        final Map<AttendanceStatus, Integer> map = initMap();
        for (final AttendanceStatus status : attendanceStatuses) {
            for (final AttendanceStatus value : values()) {
                if (status.equals(value)) {
                    map.put(value, map.get(value) + 1);
                }
            }
        }
        return map;
    }

    private static Map<AttendanceStatus, Integer> initMap() {
        final Map<AttendanceStatus, Integer> map = new HashMap<>();
        for (final AttendanceStatus status : values()) {
            map.put(status, 0);
        }
        return map;
    }

    public String getDisplayName() {
        return displayName;
    }
}
