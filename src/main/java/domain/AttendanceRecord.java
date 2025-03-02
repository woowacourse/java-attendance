package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceRecord implements Comparable<AttendanceRecord> {
    private static final LocalTime ABSENT_TIME = LocalTime.of(23, 59);
    private final LocalDateTime dateTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceRecord(LocalDate date) {
        this(LocalDateTime.of(date, ABSENT_TIME));
    }

    public AttendanceRecord(LocalDateTime dateTime) {
        validate(dateTime.toLocalDate());
        this.dateTime = dateTime;
        this.attendanceStatus = AttendanceStatus.getStatus(dateTime.getDayOfWeek(), dateTime.toLocalTime());
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public AttendanceStatus getAttendanceStatus() {
        return this.attendanceStatus;
    }

    private void validate(LocalDate date) {
        if (ClassSchedule.isDayOff(date)) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.%n", dateFormatter.format(date)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(dateTime.toLocalDate(), that.dateTime.toLocalDate()) && attendanceStatus == that.attendanceStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime.toLocalDate(), attendanceStatus);
    }

    @Override
    public int compareTo(AttendanceRecord o) {
        return this.dateTime.toLocalDate().compareTo(o.dateTime.toLocalDate());
    }
}
