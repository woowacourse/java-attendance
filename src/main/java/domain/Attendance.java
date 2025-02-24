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
    public static final LocalTime MAX_ATTENDANCE_TIME = LocalTime.of(23, 0);
    public static final LocalTime MIN_ATTENDANCE_TIME = LocalTime.of(8, 0);
    public static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    
    private LocalDateTime attendanceDateTime;
    private String attendanceStatus;
    
    public Attendance(LocalDateTime attendanceDateTime) {
        validateDate(attendanceDateTime.toLocalDate());
        validateTime(attendanceDateTime.toLocalTime());
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = checkAttendanceStatus(attendanceDateTime);
    }
    
    private void validateDate(LocalDate date) {
        // TODO : 공휴일이면 '출석 가능한 날짜가 아닙니다.'
        if (WEEKEND.contains(date.getDayOfWeek())) {
            throw new IllegalAttendDateException();
        }
    }
    
    private void validateTime(LocalTime time) {
        if (time.isAfter(MAX_ATTENDANCE_TIME) || time.isBefore(MIN_ATTENDANCE_TIME)) {
            throw new IllegalAttendTimeException();
        }
    }
    
    private String checkAttendanceStatus(LocalDateTime attendanceTime) {
        if (attendanceTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkMondayAttendanceStatus(attendanceTime);
        }
        return checkNotMondayAttendanceStatus(attendanceTime);
    }
    
    private String checkMondayAttendanceStatus(LocalDateTime attendanceTime) {
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(13, 5)) || attendanceTime.toLocalTime().equals(LocalTime.of(13, 5))) {
            return "출석";
        }
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(13, 30)) || attendanceTime.toLocalTime().equals(LocalTime.of(13, 30))) {
            return "지각";
        }
        return "결석";
    }
    
    private String checkNotMondayAttendanceStatus(LocalDateTime attendanceTime) {
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(10, 5)) || attendanceTime.toLocalTime().equals(LocalTime.of(10, 5))) {
            return "출석";
        }
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(10, 30)) || attendanceTime.toLocalTime().equals(LocalTime.of(10, 30))) {
            return "지각";
        }
        return "결석";
    }
    
    public AttendResult createAttendanceResult() {
        return new AttendResult(attendanceDateTime, attendanceStatus, true);
    }
    
    public AttendanceModifyResult modifyAttendanceTime(LocalTime newAttendanceTime) {
        LocalDateTime oldAttendanceTime = attendanceDateTime;
        attendanceDateTime = attendanceDateTime
                .withHour(newAttendanceTime.getHour())
                .withMinute(newAttendanceTime.getMinute());
        
        String oldAttendanceStatus = attendanceStatus;
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
