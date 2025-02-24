package domain;

import dto.result.AttendResult;
import dto.result.AttendanceModifyResult;
import dto.result.ExpelMeasurementResult;
import dto.result.MemberAttendResult;
import util.exception.IllegalAttendDateException;
import util.exception.IllegalAttendTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import util.status.AttendanceStatus;

public class MemberAttendances {
    
    private final CrewName crewName;
    private final List<Attendance> attendances;
    
    public MemberAttendances(String name, List<Attendance> attendances) {
        this.crewName = new CrewName(name);
        this.attendances = new ArrayList<>(attendances);
    }
    
    public ExpelMeasurementResult measureExpelRisk() {
        int lateCount = calculateLateCount();
        int absentCount = calculateAbsentCount();
        
        return new ExpelMeasurementResult(crewName.getCrewName(), lateCount, absentCount, checkStatus(lateCount, absentCount));
    }
    
    public MemberAttendResult getAttendanceResult() {
        int attendCount = calculateAttendCount();
        int lateCount = calculateLateCount();
        int absentCount = calculateAbsentCount();
        
        return new MemberAttendResult(crewName.getCrewName(),
                attendances.stream().map(Attendance::createAttendanceResult).toList(),
                attendCount,
                lateCount,
                absentCount,
                checkStatus(lateCount, absentCount)
        );
    }
    
    public int calculateAttendCount() {
        int count = 0;
        
        List<AttendResult> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendResult attendanceResult : attendanceResults) {
            if (attendanceResult.attendanceStatus().equals(AttendanceStatus.ATTENDANCE.getMessage())) {
                count++;
            }
        }
        return count;
    }
    
    public int calculateLateCount() {
        int count = 0;
        
        List<AttendResult> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendResult attendanceResult : attendanceResults) {
            if (attendanceResult.attendanceStatus().equals(AttendanceStatus.LATE.getMessage())) {
                count++;
            }
        }
        return count;
    }
    
    public int calculateAbsentCount() {
        int count = 0;
        
        List<AttendResult> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendResult attendanceResult : attendanceResults) {
            if (attendanceResult.attendanceStatus().equals(AttendanceStatus.ABSENT.getMessage())) {
                count++;
            }
        }
        return count;
    }
    
    public String checkStatus(int lateCount, int absentCount) {
        int expelRiskMeasurement = absentCount + lateCount / 3;
        if (expelRiskMeasurement > 5) return AttendanceStatus.EXPELLED.getMessage();
        if (expelRiskMeasurement >= 3) return AttendanceStatus.INTERVIEW.getMessage();
        if (expelRiskMeasurement >= 2) return AttendanceStatus.DANGER.getMessage();
        return null;
    }
    
    public AttendResult attend(LocalDateTime attendDateTime) {
        Attendance newAttendance = new Attendance(attendDateTime);
        attendances.add(newAttendance);
        return newAttendance.createAttendanceResult();
    }
    
    public AttendanceModifyResult modifyAttendance(LocalDate date, LocalTime time) {
        for (Attendance attendance : attendances) {
            if (attendance.isSameDay(date)) {
                return attendance.modifyAttendanceTime(time);
            }
        }
        
        try {
            Attendance newAttendance = new Attendance(LocalDateTime.of(date, time));
            return new AttendanceModifyResult(date, null, null, time, newAttendance.createAttendanceResult().attendanceStatus());
        } catch (IllegalAttendDateException e) {
            throw new IllegalAttendDateException("수정 가능한 날짜가 아닙니다.");
        } catch (IllegalAttendTimeException e) {
            throw new IllegalAttendTimeException("수정 가능한 시간이 아닙니다.");
        }
    }
}
