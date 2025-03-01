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

    public static AttendanceStatus from(WorkDateTime workDateTime) {
        Integer hour = workDateTime.getTime().getHour().orElse(null);
        Integer minute = workDateTime.getTime().getMinute().orElse(null);

        if (hour == null || minute == null) {
            return ABSENCE;
        }

        WorkDay currentDay = workDateTime.getDate().getWorkDay();
        return determineAttendanceStatus(currentDay, hour, minute);
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
            List<WorkDateTime> workDateTimes) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = initializeAttendanceMap();
        workDateTimes.forEach(workDateTime -> {
            AttendanceStatus status = from(workDateTime);
            attendanceStatusCount.put(status, attendanceStatusCount.get(status) + 1);
        });

        return attendanceStatusCount;
    }

    private static Map<AttendanceStatus, Integer> initializeAttendanceMap() {
        return new HashMap<>(Map.of(
                ATTENDANCE, 0,
                PERCEPTION, 0,
                ABSENCE, 0
        ));
    }

    public String getName() {
        return name;
    }
}
