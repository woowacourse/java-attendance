package domain;

import dto.result.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    
    private static final class ExpelMeasurementComparator implements Comparator<ExpelMeasurementResult> {
        @Override
        public int compare(ExpelMeasurementResult o1, ExpelMeasurementResult o2) {
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
    
    private final Map<CrewName, MemberAttendances> memberAttendances;
    
    public AttendanceBook(Map<CrewName, MemberAttendances> memberAttendances) {
        this.memberAttendances = memberAttendances;
    }
    
    public AttendResult addAttendance(String name, LocalDateTime attendDateTime) {
        CrewName crewName = new CrewName(name);
        validateName(crewName);
        MemberAttendances memberAttendance = memberAttendances.get(crewName);
        return memberAttendance.attend(attendDateTime);

    }
    
    public MemberAttendanceModifyResult editAttendance(String name, LocalDate date, LocalTime time) {
        CrewName crewName = new CrewName(name);
        validateName(crewName);
        MemberAttendances memberAttendance = memberAttendances.get(crewName);
        AttendanceModifyResult result = memberAttendance.modifyAttendance(date, time);
        return new MemberAttendanceModifyResult(crewName.getCrewName(), result.attendanceDate(), result.oldAttendanceTime(), result.oldAttendanceStatus(), result.newAttendanceTime(), result.newAttendanceStatus());
    }
    
    private void validateName(CrewName crewName) {
        if (memberAttendances.get(crewName) == null) {
            throw new IllegalArgumentException("해당 멤버는 존재하지 않습니다.");
        }
    }
    
    public MemberAttendResult getAttendanceResult(String name) {
        MemberAttendances oneMemberAttendances = memberAttendances.get(new CrewName(name));
        return oneMemberAttendances.getAttendanceResult();
    }
    
    public List<ExpelMeasurementResult> checkExpelWarnings() {
        return memberAttendances.values().stream()
                .map(MemberAttendances::measureExpelRisk)
                .filter(dto -> dto.measurementName() != null)
                .sorted(new ExpelMeasurementComparator())
                .toList();
    }
}
