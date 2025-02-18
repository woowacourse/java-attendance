package domain;

import dto.AttendanceResultDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Attendance {
    public static final LocalTime MAX_ATTENDANCE_TIME = LocalTime.of(23, 0);
    public static final LocalTime MIN_ATTENDANCE_TIME = LocalTime.of(8, 0);
    public static final List<DayOfWeek> WEEKEND = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    
    private String name;
    private LocalDateTime attendanceTime;
    private String attendanceStatus;
    
    public Attendance(String name, LocalDateTime attendanceTime) {
        validateDate(attendanceTime.toLocalDate());
        validateTime(attendanceTime.toLocalTime());
        this.name = name;
        this.attendanceTime = attendanceTime;
        this.attendanceStatus = checkAttendanceStatus(attendanceTime);
    }
    
    private void validateDate(LocalDate date) {
        // TODO : 주말, 공휴일이면 '출석 가능한 날짜가 아닙니다.'
        if (WEEKEND.contains(date.getDayOfWeek())) {
            throw new IllegalArgumentException("출석 가능한 날짜가 아닙니다.");
        }
    }
    
    private void validateTime(LocalTime time) {
        if (time.isAfter(MAX_ATTENDANCE_TIME) || time.isBefore(MIN_ATTENDANCE_TIME)) {
            throw new IllegalArgumentException("출석 가능한 시간이 아닙니다.");
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
    
    public AttendanceResultDTO createAttendanceResult() {
        return new AttendanceResultDTO(name, attendanceTime, attendanceStatus);
    }
}
