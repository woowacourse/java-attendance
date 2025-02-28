package domain;

import java.time.LocalDate;

public class EmptyAttendanceRecord extends AbstractAttendanceRecord {

    private EmptyAttendanceRecord(Crew crew, LocalDate date) {
        super(crew, date, AttendanceStatus.ABSENT);
    }

    public static EmptyAttendanceRecord of(Crew crew, LocalDate date) {
        return new EmptyAttendanceRecord(crew, date);
    }
}
