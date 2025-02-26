package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class Attendance {
    private final LocalDate date;
    private final LocalTime time;
    private final AttendanceStatus status;

    private Attendance(LocalDate date, LocalTime time, AttendanceStatus status) {
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public static Attendance empty(LocalDate date) {
        return new Attendance(date, null, AttendanceStatus.ABSENCE);
    }

    public static Attendance of(LocalDate date, LocalTime time) {
        return new Attendance(date, time, AttendanceStatus.of(LocalDateTime.of(date, time)));
    }

    public boolean isAbsence() {
        return status == AttendanceStatus.ABSENCE;
    }

    public boolean isAttendedOn(LocalDate date) {
        return this.date.isEqual(date);
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<LocalTime> getTime() {
        if (time == null) {
            return Optional.empty();
        }
        return Optional.of(time);
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public Attendance modify(LocalTime updatedTime) {
        return new Attendance(date, updatedTime, AttendanceStatus.of(LocalDateTime.of(date, updatedTime)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attendance other = (Attendance) obj;
        return date.isEqual(other.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
