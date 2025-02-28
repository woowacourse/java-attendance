package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    private final LocalDateTime dateTime;

    public Attendance(LocalDateTime dateTime) {
        validate(dateTime);
        this.dateTime = dateTime;
    }

    public boolean has(LocalDate day) {
        return this.dateTime.toLocalDate().isEqual(day);
    }

    private void validate(LocalDateTime dateTime) {
        if (isHolidayOrWeekend(dateTime.toLocalDate())) {
            throw new IllegalArgumentException("주말 또는 공휴일에는 출석할 수 없습니다.");
        }
    }

    private boolean isHolidayOrWeekend(LocalDate localDate) {
        return false;
    }
}
