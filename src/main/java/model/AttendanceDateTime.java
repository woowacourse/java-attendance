package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDateTime implements Comparable<AttendanceDateTime> {
    private final LocalDateTime attendanceDateTime;

    public AttendanceDateTime(LocalDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public boolean isSameAttendanceDateTime(AttendanceDateTime wantToCheckAttendanceDateTime) {
        return attendanceDateTime.toLocalDate().isEqual(wantToCheckAttendanceDateTime.toLocalDate());
    }

    public LocalDate toLocalDate() {
        return attendanceDateTime.toLocalDate();
    }

    public boolean isWeekend() {
        return attendanceDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY) || attendanceDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public boolean isChristmas() {
        return attendanceDateTime.getDayOfMonth() == 25;
    }

    public AttendanceDateTime addOneDay() {
        return new AttendanceDateTime(attendanceDateTime.plusDays(1));
    }

    @Override
    public int compareTo(AttendanceDateTime other) {
        return this.attendanceDateTime.compareTo(other.attendanceDateTime);
    }

    public LocalTime toLocalTime() {
        return attendanceDateTime.toLocalTime();
    }

    public boolean isMonday() {
        return attendanceDateTime.getDayOfWeek().equals(DayOfWeek.MONDAY);
    }

    public DayOfWeek toDayOfWeek() {
        return attendanceDateTime.getDayOfWeek();
    }

    public boolean isZeroTime(DateTimeFormatter dateTimeFormatter) {
        return attendanceDateTime.format(dateTimeFormatter).equals("00:00");
    }

    public LocalDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }
}
