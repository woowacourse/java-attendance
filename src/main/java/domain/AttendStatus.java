package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public enum AttendStatus {
    ATTEND,
    LATE,
    ABSENCE;

    private static final int LATE_TIME_BOUND = 5;
    private static final int ABSENCE_TIME_BOUND = 30;

    public static AttendStatus checkAttendStatus(final LocalDate localDate, final LocalTime localTime) {
        LocalTime educationTime = EducationTime.getEducationStartTime(localDate);
        LocalTime lateTime = educationTime.plusMinutes(LATE_TIME_BOUND);
        LocalTime absenceTime = educationTime.plusMinutes(ABSENCE_TIME_BOUND);
        if (localTime == null || localTime.isAfter(absenceTime)) {
            return AttendStatus.ABSENCE;
        }
        if (localTime.isAfter(lateTime) && !localTime.isAfter(absenceTime)) {
            return AttendStatus.LATE;
        }
        return AttendStatus.ATTEND;
    }
}
