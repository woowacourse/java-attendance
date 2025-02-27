package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Time {

    private LocalDateTime attendanceTime;

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

    public boolean isSameLocalDate(LocalDate time) {
        return this.attendanceTime.getYear() == time.getYear()
                && this.attendanceTime.getMonth() == time.getMonth()
                && this.attendanceTime.getDayOfMonth() == time.getDayOfMonth();
    }


    public void modify(LocalTime modifyTime) {
        attendanceTime = attendanceTime
                .withHour(modifyTime.getHour())
                .withMinute(modifyTime.getMinute());
    }

    public LocalDate getLocalDate() {
        return attendanceTime.toLocalDate();
    }
}
