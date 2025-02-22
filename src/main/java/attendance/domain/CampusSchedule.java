package attendance.domain;

import java.time.LocalTime;

public enum CampusSchedule {
    CAMPUS_OPEN_TIME(LocalTime.of(8, 0, 0)),
    CAMPUS_CLOSE_TIME(LocalTime.of(23, 0, 0)),
    MONDAY_EDUCATION_START_TIME(LocalTime.of(13, 0, 0)),
    MONDAY_EDUCATION_END_TIME(LocalTime.of(18, 0, 0)),
    NOT_MONDAY_EDUCATION_START_TIME(LocalTime.of(10, 0, 0)),
    NOT_MONDAY_EDUCATION_END_TIME(LocalTime.of(18, 0, 0));

    private final LocalTime time;

    CampusSchedule(LocalTime time) {
        this.time = time;
    }

    public static AttendanceType checkAttendance(boolean isMonday, LocalTime time) {
        CampusSchedule.validateIsInCampusTime(time);
        if (isMonday) {
            return AttendanceType.parse(MONDAY_EDUCATION_START_TIME.getTime(), time);
        }
        return AttendanceType.parse(NOT_MONDAY_EDUCATION_START_TIME.getTime(), time);
    }

    private static void validateIsInCampusTime(LocalTime time) {
        boolean isNotInCampusTime = time.isBefore(CAMPUS_OPEN_TIME.time) || time.isAfter(CAMPUS_CLOSE_TIME.time);
        if (isNotInCampusTime) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영시간이 아닙니다.");
        }
    }

    public LocalTime getTime() {
        return time;
    }
}
