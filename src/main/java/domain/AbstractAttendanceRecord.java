package domain;

import java.time.LocalDate;
import util.DateTimeUtil;
import util.Validator;

public abstract class AbstractAttendanceRecord {
    
    private final Crew crew;
    private final LocalDate date;
    private final AttendanceStatus status;

    public AbstractAttendanceRecord(Crew crew, LocalDate date, AttendanceStatus status) {
        validateCrew(crew);
        validateDate(date);
        validateStatus(status);

        this.date = date;
        this.status = status;
        this.crew = crew;
    }

    private void validateCrew(Crew crew) {
        Validator.validateNull(crew);
    }

    private void validateDate(LocalDate date) {
        Validator.validateNull(date);
        validateOffDate(date);
    }

    private void validateOffDate(LocalDate date) {
        if (DateTimeUtil.isWeekend(date)
                || Holiday.isHoliday(date)) {
            throw new IllegalArgumentException(date + ": 주말 및 공휴일에는 출석을 기록할 수 없습니다.");
        }
    }

    private void validateStatus(AttendanceStatus status) {
        Validator.validateNull(status);
    }

    public Crew getCrew() {
        return crew;
    }

    public LocalDate getDate() {
        return date;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}
