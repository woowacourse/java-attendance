package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceRecord {
    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;

    private final LocalDate date;
    private final LocalTime time;
    private final Attendance attendance;

    public AttendanceRecord(String dateTime) {
        this.date = LocalDate.parse(dateTime.split(" ")[DATE_INDEX]);
        validateDate(date);
        this.time = LocalTime.parse(dateTime.split(" ")[TIME_INDEX]);
        this.attendance = Attendance.getAttendanceStatus(Day.getDay(date), time);
    }

    public AttendanceRecord(LocalTime time, DateGenerator dateGenerator) {
        this.date = dateGenerator.generate();
        validateDate(date);
        this.time = time;
        this.attendance = Attendance.getAttendanceStatus(Day.getDay(date), time);
    }

    public AttendanceRecord(LocalDate date) {
        this.date = date;
        this.time = LocalTime.of(0, 0);
        this.attendance = Attendance.ABSENT;
    }

    public AttendanceRecord(LocalDate date, LocalTime time) {
        this.date = date;
        validateDate(date);
        this.time = time;
        this.attendance = Attendance.getAttendanceStatus(Day.getDay(date), time);
    }

    private void validateDate(LocalDate date) {
        if (Day.checkHoliday(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    Day.getDay(date).getName()));
        }
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time) && attendance == that.attendance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, attendance);
    }

    public boolean isPresent() {
        return attendance.equals(Attendance.PRESENT);
    }

    public boolean isTardy() {
        return attendance.equals(Attendance.TARDY);
    }

    public boolean isAbsent() {
        return attendance.equals(Attendance.ABSENT);
    }

    public LocalTime getTime() {
        return time;
    }

    public Attendance getAttendance() {
        return attendance;
    }
}
