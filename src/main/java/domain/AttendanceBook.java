package domain;

import dto.result.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    
    private final Map<String, MemberAttendances> memberAttendances;
    
    public AttendanceBook(Map<String, MemberAttendances> memberAttendances) {
        this.memberAttendances = memberAttendances;
    }
    
    public AttendResult addAttendance(String name, LocalDateTime attendDateTime) {
        validateName(name);
        MemberAttendances memberAttendance = memberAttendances.get(name);
        return memberAttendance.attend(attendDateTime);
    }
    
    public MemberAttendanceModifyResult editAttendance(String name, LocalDate date, LocalTime time) {
        validateName(name);
        MemberAttendances memberAttendance = memberAttendances.get(name);
        AttendanceModifyResult result = memberAttendance.modifyAttendance(date, time);
        return new MemberAttendanceModifyResult(name, result.attendanceDate(), result.oldAttendanceTime(), result.oldAttendanceStatus(), result.newAttendanceTime(), result.newAttendanceStatus());
    }
    
    private void validateName(String name) {
        if (memberAttendances.get(name) == null) {
            throw new IllegalArgumentException("해당 멤버는 존재하지 않습니다.");
        }
    }
    
    public MemberAttendResult getAttendanceResult(String name) {
        MemberAttendances oneMemberAttendances = memberAttendances.get(name);
        return oneMemberAttendances.getAttendanceResult();
    }
    
    public List<ExpelMeasurementResult> createExpelWarnings() {
        return memberAttendances.values().stream()
                .map(MemberAttendances::measureExpelRisk)
                .toList();
    }
}
