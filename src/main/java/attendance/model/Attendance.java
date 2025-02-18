package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Attendance {

    private final Crew crew;
    private final LocalDateTime dateTime;

    public Attendance(Crew crew, LocalDateTime dateTime) {
        this.crew = crew;
        this.dateTime = dateTime;
        validateDateTime(dateTime);
    }

    private void validateDateTime(LocalDateTime dateTime) {
        boolean isWeekend = dateTime.getDayOfWeek() == DayOfWeek.SATURDAY || dateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
        if (isWeekend) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(dateTime)) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }
}
