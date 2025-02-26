package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Attendance {

    private final LocalDateTime localDateTime;

    public Attendance(LocalDateTime localDateTime) {
        validateWeekday(localDateTime);
        this.localDateTime = localDateTime;
    }

    public void validateWeekday(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] 주말은 등교일이 아닙니다.");
        }
    }
}
