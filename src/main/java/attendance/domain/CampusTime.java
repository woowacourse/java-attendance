package attendance.domain;

import java.time.LocalTime;

public enum CampusTime {

    CAMPUS_OPEN_TIME(LocalTime.of(8, 0)),
    CAMPUS_CLOSE_TIME(LocalTime.of(23, 0)),
    MONDAY_LECTURE_START_TIME(LocalTime.of(13, 0)),
    TUESDAY_TO_FRIDAY_LECTURE_START_TIME(LocalTime.of(10, 0)),
    LECTURE_CLOSE_TIME(LocalTime.of(18, 0));

    private final LocalTime localTime;

    CampusTime(final LocalTime localTime) {
        this.localTime = localTime;
    }

    public static boolean isOutOfCampusOperationTime(final LocalTime targetTime) {
        return CAMPUS_OPEN_TIME.getLocalTime().isAfter(targetTime)
                || CAMPUS_CLOSE_TIME.getLocalTime().isBefore(targetTime);
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

}
