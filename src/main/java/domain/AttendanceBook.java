package domain;

import dto.AttendanceModifyDTO;
import dto.AttendanceModifyResult;
import dto.AttendanceResultDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class AttendanceBook {
    
    private final Map<String, MemberAttendances> memberAttendances;
    
    public AttendanceBook(Map<String, MemberAttendances> memberAttendances) {
        this.memberAttendances = memberAttendances;
    }
    
    public AttendanceResultDTO addAttendance(String name, LocalDateTime attendDateTime) {
        validateName(name);
        MemberAttendances memberAttendance = memberAttendances.get(name);
        return memberAttendance.attend(attendDateTime);
    }
    
    public AttendanceModifyResult editAttendance(String name, LocalDate date, LocalTime time) {
        validateName(name);
        MemberAttendances memberAttendance = memberAttendances.get(name);
        AttendanceModifyDTO result = memberAttendance.modifyAttendance(date, time);
        return new AttendanceModifyResult(name, result.attendanceDate(), result.oldAttendanceTime(), result.oldAttendanceStatus(), result.newAttendanceTime(), result.newAttendanceStatus());
    }
    
    private void validateName(String name) {
        if (memberAttendances.get(name) == null) {
            throw new IllegalArgumentException("해당 멤버는 존재하지 않습니다.");
        }
    }
}
