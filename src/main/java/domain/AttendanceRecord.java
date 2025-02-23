package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceRecord {
    public static final LocalTime ABSENT_TIME = LocalTime.of(14, 0);

    private final LocalDateTime dateTime;
    private final Attendance attendance;

    private AttendanceRecord(LocalDateTime dateTime) {
        validateDate(dateTime);
        this.dateTime = dateTime;
        this.attendance = Attendance.getAttendanceStatus(Day.getDay(dateTime.toLocalDate()), dateTime.toLocalTime());
    }

    public static AttendanceRecord parse(String dateTime) {
        return new AttendanceRecord(LocalDateTime.parse(dateTime.replace(" ", "T")));
    }

    public static AttendanceRecord of(LocalDate date, LocalTime time) {
        return new AttendanceRecord(LocalDateTime.of(date, time));
    }

    public static AttendanceRecord checkIn(LocalTime time, DateGenerator dateGenerator) {
        return new AttendanceRecord(LocalDateTime.of(dateGenerator.generate(), time));
    }

    public static AttendanceRecord asAbsent(LocalDate date) {
        return new AttendanceRecord(LocalDateTime.of(date, ABSENT_TIME));
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public boolean isTardy() {
        return attendance.equals(Attendance.TARDY);
    }

    public boolean isAbsent() {
        return attendance.equals(Attendance.ABSENT);
    }

    private void validateDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
        String formattedDateTime = dateTime.format(formatter);
        if (Day.checkHoliday(dateTime.toLocalDate())) {
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.", formattedDateTime));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(dateTime, that.dateTime) && attendance == that.attendance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, attendance);
    }
}
