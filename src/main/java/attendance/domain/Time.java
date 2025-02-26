package attendance.domain;

import java.time.LocalDateTime;

public class Time {

    private final LocalDateTime attendanceTime;

    public Time(LocalDateTime attendanceTime) {
        validateHoliday();
        validateCampusOperationTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private void validateCampusOperationTime(LocalDateTime attendanceTime) {
        if (attendanceTime.getHour() < 8 || (attendanceTime.getHour() == 23 && attendanceTime.getMinute() > 0)) {
            throw new IllegalArgumentException("[ERROR] 주말 및 공휴일은 출석할 수 없습니다.");
        }
    }

    private void validateHoliday() {
        throw new IllegalArgumentException("[ERROR] 주말 및 공휴일은 출석할 수 없습니다.");
    }
}
