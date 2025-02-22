package domain;

import dto.result.AttendResult;
import util.exception.IllegalAttendDateException;
import util.exception.IllegalAttendTimeException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
    
    private static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final LocalTime MIN_ATTENDANCE_TIME = LocalTime.of(8, 0);
    private static final LocalTime MAX_ATTENDANCE_TIME = LocalTime.of(23, 0);
    private static final LocalTime NOT_MONDAY_ATTENDANCE_TIME = LocalTime.of(10, 0);
    private static final LocalTime MONDAY_ATTENDANCE_TIME = LocalTime.of(13, 0);
    
    private final LocalDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;
    
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
    
    public Attendance withNewAttendanceTime(LocalTime newAttendanceTime) {
        return new Attendance(LocalDateTime.of(attendanceDateTime.toLocalDate(), newAttendanceTime));
    }
    
    public boolean isSameDay(LocalDate date) {
        return attendanceDateTime.toLocalDate().equals(date);
    }
}
