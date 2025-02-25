package attendance.domain;

import java.time.LocalDateTime;

public class Time {

    private final LocalDateTime attendanceTime;

    public Time(LocalDateTime attendanceTime) {
        validateCampusOperationTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private void validateCampusOperationTime(LocalDateTime attendanceTime) {
        if (attendanceTime.getHour() < 8 || (attendanceTime.getHour() == 23 && attendanceTime.getMinute() > 0)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }
}
