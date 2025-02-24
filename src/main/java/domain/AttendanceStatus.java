package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AttendanceStatus {
    ATTENDANCE("출석", 0),
    PERCEPTION("지각", 5),
    ABSENCE("결석", 30);

    private final String name;
    private final int limitTime;

    AttendanceStatus(String name, int limitMinute) {
        this.name = name;
        this.limitTime = limitMinute;
    }

    public static AttendanceStatus findByDateTime(DateTime dateTime) {
        Integer hour = dateTime.getTime().getHour();
        Integer minute = dateTime.getTime().getMinute();
        if (hour == null || minute == null) {
            return ATTENDANCE;
        }

        validateTime(hour);
        WorkDay today = dateTime.getDate().getWorkDay();
        validateWorkDay(today);

        return determineAttendanceStatus(today, dateTime.getTime().getHour(), dateTime.getTime().getMinute());
    }

    private static void validateTime(Integer hour) {
        if (hour < 8 || hour > 18) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private static void validateWorkDay(WorkDay workDay) {
        if (workDay.isWeekend()) {
            throw new IllegalArgumentException("주말에는 출석할 수 없습니다.");
        }
    }

    private static AttendanceStatus determineAttendanceStatus(WorkDay today, Integer hour, Integer minute) {
        Integer startHour = today.getStartHour();

        if (hour > startHour || (hour.equals(startHour) && minute > ABSENCE.limitTime)) {
            return ABSENCE;
        }
        if (hour.equals(startHour) && minute > PERCEPTION.limitTime) {
            return PERCEPTION;
        }

        return ATTENDANCE;
    }

    public static Map<AttendanceStatus, Integer> calculateAttendanceStatusCount(
            List<AttendanceStatus> attendanceStatuses) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = initializeAttendanceMap();
        attendanceStatuses.forEach(status -> attendanceStatusCount.put(status, attendanceStatusCount.get(status) + 1));

        return attendanceStatusCount;
    }

    private static Map<AttendanceStatus, Integer> initializeAttendanceMap() {
        return new HashMap<>(Map.of(
                ATTENDANCE, 0,
                PERCEPTION, 0,
                ABSENCE, 0
        ));
    }

    public boolean isPerception() {
        return this == PERCEPTION;
    }

    public boolean isAbsence() {
        return this == ABSENCE;
    }

    public String getName() {
        return name;
    }
}
