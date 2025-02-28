package domain;

import java.time.LocalDate;

public class EmptyAttendanceRecord extends AbstractAttendanceRecord {
    public EmptyAttendanceRecord(Crew crew, LocalDate date, AttendanceStatus status) {
        super(crew, date, status);
    }
}
