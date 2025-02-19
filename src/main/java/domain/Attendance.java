package domain;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {
    private final Crew crew;
    private final LocalDateTime time;

    public Attendance(Crew crew, LocalDateTime time) {
        this.crew = crew;
        this.time = time;
    }

    public boolean isSameDateWith(LocalDateTime dateTime) {
        return dateTime.toLocalDate().isEqual(time.toLocalDate());
    }

    public Crew getCrew() {
        return crew;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attendance other = (Attendance) obj;
        return crew.equals(other.crew) && time.isEqual(other.time);
    }
}
