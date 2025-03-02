package domain;

import static domain.AttendanceCode.ABSENT;
import static domain.AttendanceCode.LATE;
import static domain.AttendanceCode.PRESENT;
import static domain.DayOfWeek.MONDAY;
import static domain.DayOfWeek.TUESDAY;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import util.DayConverter;

public class Attendance implements Comparable<Attendance> {
    private final LocalDateTime attendanceTime;

    public Attendance(LocalDateTime attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public AttendanceCode calculateAttendanceCode() {
        LocalTime time = attendanceTime.toLocalTime();
        Duration duration = Duration.between(TUESDAY.getOpenTime(), time);
        if (attendanceTime.getDayOfWeek().getValue() == MONDAY.getDayOfWeekCode()) {
            duration = Duration.between(MONDAY.getOpenTime(), time);
        }

        if (duration.toMinutes() <= PRESENT.getUpperBound()) {
            return PRESENT;
        }
        if (duration.toMinutes() >= LATE.getLowerBound() && duration.toMinutes() <= LATE.getUpperBound()) {
            return LATE;
        }
        return ABSENT;
    }

    public boolean isHoliday(List<Integer> holidays) {
        DayOfMonth dayOfMonth = new DayOfMonth(attendanceTime.getDayOfMonth());
        return dayOfMonth.isHoliday(holidays, attendanceTime.toLocalDate());
    }

    public void validateHoliday(List<Integer> holidays) {
        if (isHoliday(holidays)) {
            throw new IllegalArgumentException(
                    String.format("%d월 %d일 %s은 등교일이 아닙니다.",
                            attendanceTime.getMonth().getValue(),
                            attendanceTime.getDayOfMonth(),
                            DayConverter.getKoreanDayOfWeek(attendanceTime.toLocalDate())));
        }
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public boolean isSameDay(LocalDate localDate) {
        return attendanceTime.toLocalDate().equals(localDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(attendanceTime, that.attendanceTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceTime);
    }

    @Override
    public int compareTo(Attendance o) {
        if (attendanceTime.isAfter(o.getAttendanceTime())) {
            return 1;
        }
        return -1;
    }
}
