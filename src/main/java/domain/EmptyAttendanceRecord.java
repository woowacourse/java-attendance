package domain;

import java.time.LocalDate;

public class EmptyAttendanceRecord extends AbstractAttendanceRecord {

    private EmptyAttendanceRecord(Crew crew, LocalDate date) {
        super(crew, date, AttendanceStatus.ABSENT);
    }

    public static AbstractAttendanceRecord of(Crew crew, LocalDate date) {
        return new EmptyAttendanceRecord(crew, date);
    }

    @Override
    public boolean isPresent() {
        return false;
    }
}
