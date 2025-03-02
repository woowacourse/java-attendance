package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class EmptyAttendance implements Attendance {
    private final AttendanceDate date;

    private EmptyAttendance(LocalDate date) {
        this.date = new AttendanceDate(date);
    }

    public static EmptyAttendance of(LocalDate date) {
        return new EmptyAttendance(date);
    }

    @Override
    public boolean isAttendedOn(LocalDate date) {
        return this.date.getValue().isEqual(date);
    }

    @Override
    public boolean isTimeRecorded() {
        return false;
    }

    @Override
    public LocalDate getDate() {
        return date.getValue();
    }

    @Override
    public LocalTime getTime() {
        throw new RuntimeException("무단 결석은 시간 기록이 존재하지 않습니다.");
    }

    @Override
    public AttendanceStatus getStatus() {
        return AttendanceStatus.ABSENCE;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EmptyAttendance that = (EmptyAttendance) object;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
