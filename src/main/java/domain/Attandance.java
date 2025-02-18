package domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class Attandance {
    private LocalDateTime date;

    public Attandance(LocalDateTime date) {
        this.date = date;
    }

    public AttendanceStatus calculateAttendanceStatus() {
        //1. 몇요일 -> 월요일 1시부터 , 나머지 10시부터
        int dayOfWeek = date.getDayOfWeek().getValue();
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 10, 0);
        if (dayOfWeek == 1) {
            startDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 13, 0);
        }

        Duration duration = Duration.between(startDate, date);
        if (duration.toMinutes() > 5 && duration.toMinutes() <= 30) {
            return AttendanceStatus.LATE;
        }
        if (duration.toMinutes() > 30) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.PRESENT;
    }

    public void updateAttendance(LocalDateTime updateTime) {
        date = updateTime;
    }
}
