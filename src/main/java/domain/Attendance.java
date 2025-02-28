package domain;

import java.time.DayOfWeek;
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
        if (Holiday.isHoliday(dateTime.toLocalDate()) ||
                dateTime.getDayOfWeek().compareTo(DayOfWeek.FRIDAY) >= 1) {
            throw new IllegalArgumentException("주말 또는 공휴일에는 출석할 수 없습니다");
        }
//        if () {
//            throw new IllegalArgumentException("캠퍼스 운영 시간에만 출석할 수 있습니다");
//        }
    }
}
