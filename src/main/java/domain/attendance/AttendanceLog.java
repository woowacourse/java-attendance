package domain.attendance;

import dto.AttendanceLogDto;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import util.Evaluator;

public class AttendanceLog {

    private final LocalDate attendanceDate;
    private final LocalTime attendanceTime;
    private final AttendanceStatus attendanceStatus;

    public AttendanceLog(LocalDateTime attendDateTime) {
        verifyAttendDateTime(attendDateTime);
        this.attendanceDate = attendDateTime.toLocalDate();
        this.attendanceTime = attendDateTime.toLocalTime();
        this.attendanceStatus = AttendanceStatus.findStatus(attendDateTime);
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getAttendanceTime() {
        return attendanceTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public boolean isAttendDate(LocalDate attendDate) {
        return this.attendanceDate.equals(attendDate);
    }

    public AttendanceLogDto toDto() {
        return new AttendanceLogDto(attendanceDate, attendanceTime, attendanceStatus);
    }

    private void verifyAttendDateTime(LocalDateTime attendDateTime) {
        verifyAttendDate(attendDateTime.toLocalDate());
        verifyAttendTime(attendDateTime.toLocalTime());
    }

    private void verifyAttendDate(LocalDate attendDate) {
        if (!Evaluator.isOpenDate(attendDate)) {
            throw new ErrorException("주말 또는 공휴일은 출석을 받지 않습니다.");
        }
    }

    private void verifyAttendTime(LocalTime attendTime) {
        if (!Evaluator.isOpenTime(attendTime)) {
            throw new ErrorException("운영 시간에 출석해야 합니다.");
        }
    }
}
