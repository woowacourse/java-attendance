package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceRecord implements Comparable<AttendanceRecord> {
    private final LocalDateTime dateTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceRecord(LocalDateTime dateTime) {
        validate(dateTime);
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

    private void validate(LocalDateTime dateTime) {
        if (ClassSchedule.isDayOff(dateTime.toLocalDate())) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.%n", dateFormatter.format(dateTime)));
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
        return o.dateTime.toLocalDate().compareTo(this.dateTime.toLocalDate());
    }
}
