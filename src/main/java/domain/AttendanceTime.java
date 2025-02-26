package domain;

import java.time.LocalTime;

public class AttendanceTime {

    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

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

    public boolean isAfter(LocalTime time) {
        return this.attendanceTime.isAfter(time);
    }

    public boolean isBefore(LocalTime time) {
        return this.attendanceTime.isBefore(time);
    }

    public int getHour() {
        return this.attendanceTime.getHour();
    }

    public int getMinute() {
        return this.attendanceTime.getMinute();
    }
}
