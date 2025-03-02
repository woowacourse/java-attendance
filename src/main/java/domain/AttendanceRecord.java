package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceRecord extends AbstractAttendanceRecord {

    private final LocalTime time;

    private AttendanceRecord(Crew crew, LocalDate date, LocalTime time, AttendanceStatus status) {
        super(crew, date, status);

        validateTime(time);
        this.time = time;
    }

    public static AttendanceRecord of(Crew crew, LocalDate date, LocalTime time) {
        return new AttendanceRecord(crew, date, time, AttendanceStatus.of(date, time));
    }

    private void validateTime(LocalTime time) {
        CampusTimePolicy.validateCampusTime(time);
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(getDate(), time);
    }

    public LocalTime getTime() {
        return time;
    }

    public String getNickname() {
        return getCrew().getNickname();
    }
}
