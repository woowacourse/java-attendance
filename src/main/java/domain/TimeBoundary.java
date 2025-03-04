package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeBoundary(LocalTime startTime, LocalTime lateTime, LocalTime absenceTime) {

    private static final int LATE_TIME_BOUND = 5;
    private static final int ABSENCE_TIME_BOUND = 30;

    public static TimeBoundary createTimeBoundary(final LocalDate localDate) {
        LocalTime educationTime = EducationTime.getEducationStartTime(localDate);
        LocalTime lateTime = educationTime.plusMinutes(LATE_TIME_BOUND);
        LocalTime absenceTime = educationTime.plusMinutes(ABSENCE_TIME_BOUND);
        return new TimeBoundary(educationTime, lateTime, absenceTime);
    }
}
