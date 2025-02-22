package attendance.domain;

import attendance.constant.StartTimeRule;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {
    CHECKIN("출석"),
    ABSENCE("결석"),
    LATE("지각");

    public static final LocalTime CAMPUS_OPEN = LocalTime.of(8, 0);
    public static final int FROM_START_TO_LATE_GAP = 5;
    public static final int FROM_START_TO_ABSENCE_GAP = 30;
    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus compute(LocalDateTime attendAt) {
        LocalTime time = attendAt.toLocalTime();

        DayOfWeek dayOfWeek = attendAt.getDayOfWeek();
        LocalTime startTime = StartTimeRule.getStartTime(dayOfWeek);
        return determineStatus(time, startTime);
    }

    public String getName() {
        return name;
    }

    private static AttendanceStatus determineStatus(LocalTime timeToAttend, LocalTime educationStartTime) {
        LocalTime checkinThreshold = educationStartTime.plusMinutes(FROM_START_TO_LATE_GAP);
        LocalTime lateThreshold = educationStartTime.plusMinutes(FROM_START_TO_ABSENCE_GAP);

        if (timeToAttend.isBefore(CAMPUS_OPEN) || timeToAttend.isAfter(lateThreshold)) {
            return ABSENCE;
        }
        if (timeToAttend.isBefore(checkinThreshold) || timeToAttend.equals(checkinThreshold)) {
            return AttendanceStatus.CHECKIN;
        }
        return AttendanceStatus.LATE;
    }
}
