package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Time {

    private final LocalDateTime attendanceTime;

    public Time(LocalDateTime attendanceTime) {
        validateHoliday(attendanceTime);
        validateCampusOperationTime(attendanceTime);
        this.attendanceTime = attendanceTime;
    }

    private void validateCampusOperationTime(LocalDateTime attendanceTime) {
        if (attendanceTime.getHour() < 8 || (attendanceTime.getHour() == 23 && attendanceTime.getMinute() > 0)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    private void validateHoliday(LocalDateTime attendanceTime) {
        if (attendanceTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) || attendanceTime.getDayOfWeek()
                .equals(DayOfWeek.SUNDAY)) {
            throw new IllegalArgumentException("[ERROR] 주말 및 공휴일은 출석할 수 없습니다.");
        }
    }

    public boolean isSameLocalDate(Time time) {
        return this.attendanceTime.getYear() == time.attendanceTime.getYear()
                && this.attendanceTime.getMonth() == time.attendanceTime.getMonth()
                && this.attendanceTime.getDayOfMonth() == time.attendanceTime.getDayOfMonth();
    }


}
