package domain;

import dto.result.AttendResult;
import dto.result.AttendanceModifyResult;
import dto.result.ExpelMeasurementResult;
import dto.result.MemberAttendResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MemberAttendances {
    
    private final String name;
    private final List<Attendance> attendances;
    
    public MemberAttendances(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = new ArrayList<>(attendances);
    }
    
    public ExpelMeasurementResult measureExpelRisk() {
        int lateCount = calculateLateCount();
        int absentCount = calculateAbsentCount();
        
        return new ExpelMeasurementResult(
                name,
                lateCount,
                absentCount,
                ExpelRisk.of(absentCount, lateCount)
        );
    }
    
    public MemberAttendResult getAttendanceResult() {
        int attendCount = calculateAttendCount();
        int lateCount = calculateLateCount();
        int absentCount = calculateAbsentCount();
        
        return new MemberAttendResult(name,
                attendances.stream().map(Attendance::createAttendanceResult).toList(),
                attendCount,
                lateCount,
                absentCount,
                ExpelRisk.of(absentCount, lateCount)
        );
    }
    
    public int calculateAttendCount() {
        int count = 0;
        
        List<AttendResult> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendResult attendanceResult : attendanceResults) {
            if (attendanceResult.attendanceStatus() == AttendanceStatus.출석) {
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
            if (attendanceResult.attendanceStatus() == AttendanceStatus.지각) {
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
            if (attendanceResult.attendanceStatus() == AttendanceStatus.결석) {
                count++;
            }
        }
        return count;
    }
    
    public AttendResult attend(LocalDateTime attendDateTime) {
        Attendance newAttendance = new Attendance(attendDateTime);
        attendances.add(newAttendance);
        return newAttendance.createAttendanceResult();
    }
    
    public AttendanceModifyResult modifyAttendance(LocalDate date, LocalTime time) {
        for (int index = 0; index < attendances.size(); index++) {
            Attendance attendance = attendances.get(index);
            if (attendance.isSameDay(date)) {
                var newAttendance = replaceOldAttendanceAndGet(time, index);
                AttendResult oldAttendanceResult = attendance.createAttendanceResult();
                AttendResult newAttendanceResult = newAttendance.createAttendanceResult();
                return new AttendanceModifyResult(
                        oldAttendanceResult.attendanceDateTime().toLocalDate(),
                        oldAttendanceResult.attendanceDateTime().toLocalTime(), oldAttendanceResult.attendanceStatus(),
                        newAttendanceResult.attendanceDateTime().toLocalTime(), newAttendanceResult.attendanceStatus()
                );
            }
        }
        
        Attendance newAttendance = new Attendance(LocalDateTime.of(date, time));
        attendances.add(newAttendance);
        return new AttendanceModifyResult(date, null, null, time, newAttendance.createAttendanceResult().attendanceStatus());
    }
    
    private Attendance replaceOldAttendanceAndGet(LocalTime time, int index) {
        Attendance newAttendance = attendances.get(index).withNewAttendanceTime(time);
        attendances.remove(index);
        attendances.add(index, newAttendance);
        return newAttendance;
    }
}
