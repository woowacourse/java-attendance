package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private LocalDateTime localDateTime;

    public Attendance(LocalDateTime localDateTime) {
        validateWeekday(localDateTime);
        this.localDateTime = localDateTime;
    }

    private void validateWeekday(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말은 등교일이 아닙니다.");
        }
    }

    public LocalDateTime updateAttendance(LocalTime localTime) {
        localDateTime = LocalDateTime.of(localDateTime.toLocalDate(), localTime);
        return localDateTime;
    }
}
