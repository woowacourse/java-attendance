package attendance.model;

import static attendance.model.AttendanceStartTime.isCampusOpen;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Objects;

public class Attendance {

    private final Crew crew;
    private final LocalDate date;
    private final LocalTime time;

    public Attendance(Crew crew, LocalDateTime dateTime) {
        validateDate(dateTime.toLocalDate());
        validateTime(dateTime.toLocalTime());
        this.crew = crew;
        this.date = dateTime.toLocalDate();
        this.time = dateTime.toLocalTime();
    }

    public Attendance(Crew crew, LocalDate date, LocalTime time) {
        validateDate(date);
        validateTime(time);
        this.crew = crew;
        this.date = date;
        this.time = time;
    }

    private void validateDate(LocalDate date) {
        boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        if (isWeekend) {
            throw new IllegalArgumentException("주말인 경우 출석할 수 없습니다.");
        }
        if (Holiday.isHoliday(date)) {
            throw new IllegalArgumentException("법정 공휴일에는 출석할 수 없습니다.");
        }
    }

    private void validateTime(LocalTime time) {
        if (time == null) {
            return;
        }
        if (!isCampusOpen(time)) {
            throw new IllegalArgumentException("캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public boolean isCrewAttendanceInMonth(Crew crew, Month findMonth) {
        return this.crew.equals(crew) && date.getMonth() == findMonth;
    }

    public boolean isCrewAttendanceInDate(Crew crew, LocalDate date) {
        return this.crew.equals(crew) && date == this.date;
    }

    public boolean isNotRecordedTime() {
        return time == null;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDateTime getDateTime() {
        if (time == null) {
            throw new IllegalArgumentException("출석 시간이 없습니다.");
        }
        return LocalDateTime.of(date, time);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(crew, that.crew) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, date);
    }
}
