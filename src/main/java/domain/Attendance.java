package domain;

import dto.result.AttendResult;
import dto.result.AttendanceModifyResult;
import util.exception.IllegalAttendDateException;
import util.exception.IllegalAttendTimeException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
    
    public static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    public static final LocalTime MAX_ATTENDANCE_TIME = LocalTime.of(23, 0);
    public static final LocalTime MIN_ATTENDANCE_TIME = LocalTime.of(8, 0);
    public static final LocalTime MONDAY_LATE_THRESHOLD = LocalTime.of(13, 30);
    public static final LocalTime MONDAY_ATTEND_THRESHOLD = LocalTime.of(13, 5);
    public static final LocalTime NOT_MONDAY_LATE_THRESHOLD = LocalTime.of(10, 30);
    public static final LocalTime NOT_MONDAY_ATTEND_THRESHOLD = LocalTime.of(10, 5);
    
    private LocalDateTime attendanceDateTime;
    private AttendanceStatus attendanceStatus;
    
    public Attendance(LocalDateTime attendanceDateTime) {
        validateDate(attendanceDateTime.toLocalDate());
        validateTime(attendanceDateTime.toLocalTime());
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = checkAttendanceStatus(attendanceDateTime);
    }
    
    private void validateDate(LocalDate date) {
        if (WEEKEND.contains(date.getDayOfWeek())) {
            throw new IllegalAttendDateException();
        }
    }
    
    private void validateTime(LocalTime time) {
        if (time.isAfter(MAX_ATTENDANCE_TIME) || time.isBefore(MIN_ATTENDANCE_TIME)) {
            throw new IllegalAttendTimeException();
        }
    }
    
    private AttendanceStatus checkAttendanceStatus(LocalDateTime attendanceDateTime) {
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkMondayAttendanceStatus(attendanceDateTime.toLocalTime());
        }
        return checkNotMondayAttendanceStatus(attendanceDateTime.toLocalTime());
    }
    
    private AttendanceStatus checkMondayAttendanceStatus(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(MONDAY_LATE_THRESHOLD)) {
            return AttendanceStatus.결석;
        }
        if (attendanceTime.isAfter(MONDAY_ATTEND_THRESHOLD)) {
            return AttendanceStatus.지각;
        }
        return AttendanceStatus.출석;
    }
    
    private AttendanceStatus checkNotMondayAttendanceStatus(LocalTime attendanceTime) {
        if (attendanceTime.isAfter(NOT_MONDAY_LATE_THRESHOLD)) {
            return AttendanceStatus.결석;
        }
        if (attendanceTime.isAfter(NOT_MONDAY_ATTEND_THRESHOLD)) {
            return AttendanceStatus.지각;
        }
        return AttendanceStatus.출석;
    }
    
    public AttendResult createAttendanceResult() {
        return new AttendResult(attendanceDateTime, attendanceStatus, true);
    }
    
    public AttendanceModifyResult modifyAttendanceTime(LocalTime newAttendanceTime) {
        LocalDateTime oldAttendanceTime = attendanceDateTime;
        attendanceDateTime = attendanceDateTime
                .withHour(newAttendanceTime.getHour())
                .withMinute(newAttendanceTime.getMinute());
        
        AttendanceStatus oldAttendanceStatus = attendanceStatus;
        attendanceStatus = checkAttendanceStatus(attendanceDateTime);
        
        return new AttendanceModifyResult(
                attendanceDateTime.toLocalDate(),
                oldAttendanceTime.toLocalTime(),
                oldAttendanceStatus,
                attendanceDateTime.toLocalTime(),
                attendanceStatus
        );
    }
    
    public boolean isSameDay(LocalDate date) {
        return attendanceDateTime.toLocalDate().equals(date);
    }
}
