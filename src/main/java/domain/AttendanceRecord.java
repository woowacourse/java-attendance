package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.DateTimeUtil;

public class AttendanceRecord extends AbstractAttendanceRecord {
    private static final LocalTime CAMPUS_OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE_TIME = LocalTime.of(23, 0);

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
        validateCampusTime(time);
    }

    private void validateCampusTime(LocalTime time) {
        if (!DateTimeUtil.isInRange(CAMPUS_OPEN_TIME, CAMPUS_CLOSE_TIME, time)) {
            throw new IllegalArgumentException(time + ": 캠퍼스 운영시간이 아니므로 출석을 기록할 수 없습니다.");
        }
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public String getNickname() {
        return crew.getNickname();
    }
}
