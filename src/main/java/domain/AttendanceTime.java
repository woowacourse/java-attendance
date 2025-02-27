package domain;

import java.time.LocalTime;

public class AttendanceTime {

    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);
    private static final LocalTime MONDAY_EDUCATION_START_TIME = LocalTime.of(13, 0);
    private static final LocalTime EDUCATION_START_TIME = LocalTime.of(10, 0);
    private static final int LATE_JUDGEMENT_TIME = 5;
    private static final int ABSENT_JUDGEMENT_TIME = 30;

    private final LocalTime attendanceTime;

    public AttendanceTime(LocalTime time) {
        validateCampusOpen(time);
        this.attendanceTime = time;
    }

    private void validateCampusOpen(LocalTime time) {
        if (time.isBefore(CAMPUS_OPEN_TIME) || time.isAfter(CAMPUS_CLOSE_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isAfterLateTime(AttendanceDate attendanceDate) {
        LocalTime educationStartTime = EDUCATION_START_TIME;
        if (attendanceDate.isMonday()) {
            educationStartTime = MONDAY_EDUCATION_START_TIME;
        }
        LocalTime lateTime = educationStartTime.plusMinutes(LATE_JUDGEMENT_TIME);
        LocalTime absentTime = educationStartTime.plusMinutes(ABSENT_JUDGEMENT_TIME);

        return attendanceTime.isAfter(lateTime) && attendanceTime.isBefore(absentTime.plusMinutes(1));
    }

    public boolean isAfterAbsentTime(AttendanceDate attendanceDate) {
        LocalTime educationStartTime = EDUCATION_START_TIME;
        if (attendanceDate.isMonday()) {
            educationStartTime = MONDAY_EDUCATION_START_TIME;
        }

        LocalTime absentTime = educationStartTime.plusMinutes(ABSENT_JUDGEMENT_TIME);

        return this.attendanceTime.isAfter(absentTime);
    }

    public int getHour() {
        return this.attendanceTime.getHour();
    }

    public int getMinute() {
        return this.attendanceTime.getMinute();
    }
}
