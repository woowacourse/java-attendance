package domain.attendance;

import dto.AttendanceLogDto;
import exception.ErrorException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

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
        if (isWeekend(attendDate) || isHoliday(attendDate)) {
            throw new ErrorException("주말 또는 공휴일은 출석을 받지 않습니다.");
        }
    }

    private boolean isWeekend(LocalDate attendDate) {
        DayOfWeek dayOfWeek = attendDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate attendDate) {
        return attendDate.getMonth() == Month.DECEMBER && attendDate.getDayOfMonth() == 25;
    }

    private void verifyAttendTime(LocalTime attendTime) {
        if (!isOpenHours(attendTime)) {
            throw new ErrorException("운영 시간에 출석해야 합니다.");
        }
    }

    private boolean isOpenHours(LocalTime attendTime) {
        return attendTime.isAfter(LocalTime.of(8, 0, 0)) && attendTime.isBefore(LocalTime.of(23, 0));
    }
}
