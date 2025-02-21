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
    
    private AttendanceStatus checkAttendanceStatus(LocalDateTime attendanceTime) {
        if (attendanceTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return checkMondayAttendanceStatus(attendanceTime);
        }
        return checkNotMondayAttendanceStatus(attendanceTime);
    }
    
    private AttendanceStatus checkMondayAttendanceStatus(LocalDateTime attendanceTime) {
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(13, 5)) || attendanceTime.toLocalTime().equals(LocalTime.of(13, 5))) {
            return AttendanceStatus.출석;
        }
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(13, 30)) || attendanceTime.toLocalTime().equals(LocalTime.of(13, 30))) {
            return AttendanceStatus.지각;
        }
        return AttendanceStatus.결석;
    }
    
    private AttendanceStatus checkNotMondayAttendanceStatus(LocalDateTime attendanceTime) {
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(10, 5)) || attendanceTime.toLocalTime().equals(LocalTime.of(10, 5))) {
            return AttendanceStatus.출석;
            
        }
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(10, 30)) || attendanceTime.toLocalTime().equals(LocalTime.of(10, 30))) {
            return AttendanceStatus.지각;
        }
        return AttendanceStatus.결석;
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
