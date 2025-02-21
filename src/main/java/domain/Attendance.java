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
    public static final LocalTime MIN_ATTENDANCE_TIME = LocalTime.of(8, 0);
    public static final LocalTime MAX_ATTENDANCE_TIME = LocalTime.of(23, 0);
    public static final LocalTime NOT_MONDAY_ATTENDANCE_TIME = LocalTime.of(10, 0);
    public static final LocalTime MONDAY_ATTENDANCE_TIME = LocalTime.of(13, 0);
    
    private LocalDateTime attendanceDateTime;
    private AttendanceStatus attendanceStatus;
    
    public Attendance(LocalDateTime attendanceDateTime) {
        validateDate(attendanceDateTime.toLocalDate());
        validateTime(attendanceDateTime.toLocalTime());
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = determineAttendanceStatus(attendanceDateTime);
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
    
    private AttendanceStatus determineAttendanceStatus(LocalDateTime attendanceDateTime) {
        if (attendanceDateTime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceStatus.of(attendanceDateTime.toLocalTime(), MONDAY_ATTENDANCE_TIME);
        }
        return AttendanceStatus.of(attendanceDateTime.toLocalTime(), NOT_MONDAY_ATTENDANCE_TIME);
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
        attendanceStatus = determineAttendanceStatus(attendanceDateTime);
        
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
