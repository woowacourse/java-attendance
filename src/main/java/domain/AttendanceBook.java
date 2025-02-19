package domain;

import dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    
    private static final class ExpelMeasurementComparator implements Comparator<ExpelMeasurementDTO> {
        @Override
        public int compare(ExpelMeasurementDTO o1, ExpelMeasurementDTO o2) {
            if (!o1.measurementName().equals(o2.measurementName())) {
                return -o1.measurementName().compareTo(o2.measurementName());
            }
            if (o1.lateCount() + o1.absentCount() != o2.lateCount() + o2.absentCount()) {
                return -Integer.compare(o1.lateCount() + o1.absentCount(), o2.lateCount() + o2.absentCount());
            }
            if (o1.absentCount() != o2.absentCount()) {
                return -Integer.compare(o1.absentCount(), o2.absentCount());
            }
            return o1.targetName().compareTo(o2.targetName());
        }
    }
    
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
    
    public AttendanceResultDTOs getAttendanceResult(String name) {
        MemberAttendances oneMemberAttendances = memberAttendances.get(name);
        return oneMemberAttendances.getAttendanceResult();
    }
    
    public List<ExpelMeasurementDTO> checkExpelWarnings() {
        return memberAttendances.values().stream()
                .map(MemberAttendances::measureExpelRisk)
                .filter(dto -> dto.measurementName() != null)
                .sorted(new ExpelMeasurementComparator())
                .toList();
    }
}
