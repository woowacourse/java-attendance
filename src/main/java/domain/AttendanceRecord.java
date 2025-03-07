package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import util.Current;

public class AttendanceRecord {
    private final LocalDateTime localDateTime;
    private final boolean isAbsence;

    public AttendanceRecord(LocalDate date, LocalTime time) {
        this(date, time, false);
    }

    private AttendanceRecord(LocalDate date, LocalTime time, boolean isAbsence) {
        this.localDateTime = LocalDateTime.of(date, time);
        this.isAbsence = isAbsence;
    }

    public static AttendanceRecord dateOf(Integer date) {
        LocalDate localDate = Current.getToday()
                .withDayOfMonth(date);
        return from(localDate);
    }

    public static AttendanceRecord from(LocalDate date) {
        return new AttendanceRecord(date, LocalTime.MIN, true);
    }

    public boolean isSameDate(AttendanceRecord attendanceRecord) {
        return localDateTime.toLocalDate()
                .equals(attendanceRecord.localDateTime.toLocalDate());
    }

    public boolean isSameDate(Integer dateInt) {
        return localDateTime.getDayOfMonth() == dateInt;
    }

    public boolean isSameDate(LocalDate otherDate) {
        return localDateTime.toLocalDate()
                .equals(otherDate);
    }

    public LocalDate getDate() {
        return localDateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return localDateTime.toLocalTime();
    }

    public boolean isAbsence() {
        return isAbsence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceRecord that = (AttendanceRecord) o;
        return isAbsence == that.isAbsence && Objects.equals(localDateTime, that.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localDateTime, isAbsence);
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "localDateTime=" + localDateTime +
                ", isAbsence=" + isAbsence +
                '}';
    }
}
