package attendance.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDateTime dateTime;

    public Attendance(Crew crew, LocalDateTime dateTime) {
        validateDateTime(dateTime);
        this.crew = crew;
        this.dateTime = dateTime;
    }

    private void validateDateTime(LocalDateTime dateTime) {
        boolean isWeekend = dateTime.getDayOfWeek() == DayOfWeek.SATURDAY
                || dateTime.getDayOfWeek() == DayOfWeek.SUNDAY;
        if (isWeekend) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(dateTime.toLocalDate())) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }

    public boolean isCrewAttendanceInMonth(Crew crew, Month findMonth) {
        return this.crew.equals(crew) && dateTime.getMonth() == findMonth;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(dateTime.toLocalDate(), that.dateTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, dateTime.toLocalDate());
    }
}
