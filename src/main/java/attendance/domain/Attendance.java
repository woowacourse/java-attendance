package attendance.domain;

import attendance.dto.response.AttendanceRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final LocalDateTime dateTime;
    private final AttendanceStateType status;

    public Attendance(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.status = AttendanceStateType.find(EducationTime.calculateOverTime(dateTime));
    }

    public boolean isEqualDate(LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    public boolean isAlreadyCheck() {
        return !dateTime.toLocalTime().equals(LocalTime.MAX);
    }

    public boolean isEqualsStatus(AttendanceStateType status) {
        return this.status == status;
    }

    public boolean isBefore(LocalDate date) {
        return dateTime.toLocalDate().isBefore(date);
    }

    public AttendanceRecord createResponse() {
        return new AttendanceRecord(dateTime, status.getName());
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AttendanceStateType getStatus() {
        return status;
    }

    public int compareTo(Attendance other) {
        return dateTime.compareTo(other.dateTime);
    }
}
