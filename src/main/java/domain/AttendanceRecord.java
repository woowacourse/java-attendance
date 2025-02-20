package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class AttendanceRecord {
    private static final int DATE_INDEX = 0;
    private static final int TIME_INDEX = 1;
    public static final LocalTime ABSENT_TIME = LocalTime.of(14, 0);

    private final LocalDate date;
    private final LocalTime time;
    private final Attendance attendance;

    private AttendanceRecord(LocalDate date, LocalTime time) {
        this.date = date;
        validateDate(date);
        this.time = time;
        this.attendance = Attendance.getAttendanceStatus(Day.getDay(date), time);
    }

    public static AttendanceRecord parse(String dateTime) {
        return new AttendanceRecord(LocalDate.parse(dateTime.split(" ")[DATE_INDEX]),
                LocalTime.parse(dateTime.split(" ")[TIME_INDEX]));
    }

    public static AttendanceRecord of(LocalDate date, LocalTime time) {
        return new AttendanceRecord(date, time);
    }

    public static AttendanceRecord checkIn(LocalTime time, DateGenerator dateGenerator) {
        return new AttendanceRecord(dateGenerator.generate(), time);
    }

    public static AttendanceRecord asAbsent(LocalDate date) {
        return new AttendanceRecord(date, ABSENT_TIME);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Attendance getAttendance() {
        return attendance;
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

    private void validateDate(LocalDate date) {
        if (Day.checkHoliday(date)) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    Day.getDay(date).getName()));
        }
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
}
