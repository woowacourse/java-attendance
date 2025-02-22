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
        int lateCount = calculateAttendCountOf(AttendanceStatus.지각);
        int absentCount = calculateAttendCountOf(AttendanceStatus.결석);
        
        return new ExpelMeasurementResult(
                name,
                lateCount,
                absentCount,
                ExpelRisk.of(absentCount, lateCount)
        );
    }
    
    public MemberAttendResult getAttendanceResult() {
        int attendCount = calculateAttendCountOf(AttendanceStatus.출석);
        int lateCount = calculateAttendCountOf(AttendanceStatus.지각);
        int absentCount = calculateAttendCountOf(AttendanceStatus.결석);
        
        return new MemberAttendResult(name,
                attendances.stream().map(Attendance::createAttendanceResult).toList(),
                attendCount,
                lateCount,
                absentCount,
                ExpelRisk.of(absentCount, lateCount)
        );
    }
    
    public int calculateAttendCountOf(AttendanceStatus attendanceStatus) {
        int count = 0;
        
        List<AttendResult> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendResult attendanceResult : attendanceResults) {
            count = getAddedCountIfIsSame(attendanceResult, attendanceStatus, count);
        }
        return count;
    }
    
    private static int getAddedCountIfIsSame(AttendResult attendanceResult, AttendanceStatus attendanceStatus, int count) {
        if (attendanceResult.attendanceStatus() == attendanceStatus) {
            return count + 1;
        }
        return count;
    }
    
    public AttendResult attend(LocalDateTime attendDateTime) {
        Attendance newAttendance = new Attendance(attendDateTime);
        attendances.add(newAttendance);
        return newAttendance.createAttendanceResult();
    }
    
    public AttendanceModifyResult modifyAttendance(LocalDate date, LocalTime time) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDay(date))
                .findFirst()
                .map(attendance -> {
                    var newAttendance = replaceOldAttendanceAndGet(time, attendances.indexOf(attendance));
                    AttendResult oldAttendanceResult = attendance.createAttendanceResult();
                    AttendResult newAttendanceResult = newAttendance.createAttendanceResult();
                    return new AttendanceModifyResult(
                            oldAttendanceResult.attendanceDateTime().toLocalDate(),
                            oldAttendanceResult.attendanceDateTime().toLocalTime(),
                            oldAttendanceResult.attendanceStatus(),
                            newAttendanceResult.attendanceDateTime().toLocalTime(),
                            newAttendanceResult.attendanceStatus()
                    );
                })
                .orElseGet(() -> {
                    Attendance newAttendance = new Attendance(LocalDateTime.of(date, time));
                    attendances.add(newAttendance);
                    return new AttendanceModifyResult(date, null, null, time, newAttendance.createAttendanceResult().attendanceStatus());
                });
    }
    
    private Attendance replaceOldAttendanceAndGet(LocalTime time, int index) {
        Attendance newAttendance = attendances.get(index).withNewAttendanceTime(time);
        attendances.remove(index);
        attendances.add(index, newAttendance);
        return newAttendance;
    }
}
