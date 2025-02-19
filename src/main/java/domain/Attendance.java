package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private final LocalDateTime time;

    public Attendance(LocalDateTime time) {
        this.time = time;
    }

    public boolean isSameDateWith(LocalDateTime dateTime) {
        return dateTime.toLocalDate().isEqual(time.toLocalDate());
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getStatus() {
        DayOfWeek dayOfWeek = time.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            // 13:00 기준
            if (time.toLocalTime().toNanoOfDay() <= LocalTime.of(13, 5, 0).toNanoOfDay()) { // 따로 저장해두기 (enum ...)
                return "출석";
            }
            if (time.toLocalTime().toNanoOfDay() <= LocalTime.of(13, 30, 0).toNanoOfDay()) {
                return "지각";
            }
            return "결석";
        }
        // 10:00
        if (time.toLocalTime().toNanoOfDay() <= LocalTime.of(10, 0, 0).toNanoOfDay()) {
            return "출석";
        }
        if (time.toLocalTime().toNanoOfDay() <= LocalTime.of(10, 30, 0).toNanoOfDay()) {
            return "지각";
        }
        return "결석";
    }

    public Attendance modify(int newHour, int newMinutes) {
        return new Attendance(time.withHour(newHour).withMinute(newMinutes));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attendance other = (Attendance) obj;
        return time.isEqual(other.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }
}
