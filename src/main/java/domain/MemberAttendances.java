package domain;

import dto.AttendanceModifyDTO;
import dto.AttendanceResultDTO;
import dto.AttendanceResultDTOs;
import dto.ExpelMeasurementDTO;
import util.exception.IllegalAttendDateException;
import util.exception.IllegalAttendTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MemberAttendances {
    
    private String name;
    private List<Attendance> attendances;
    
    public MemberAttendances(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = new ArrayList<>(attendances);
    }
    
    public ExpelMeasurementDTO measureExpelRisk() {
        int lateCount = 0;
        int absentCount = 0;
        List<AttendanceResultDTO> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendanceResultDTO attendanceResult : attendanceResults) {
            if (attendanceResult.attendanceStatus().equals("지각")) {
                lateCount++;
                continue;
            }
            if (attendanceResult.attendanceStatus().equals("결석")) {
                absentCount++;
            }
        }
        
        int expelRiskMeasurement = absentCount + lateCount / 3;
        if (expelRiskMeasurement > 5) return new ExpelMeasurementDTO(name, lateCount, absentCount, "제적");
        if (expelRiskMeasurement >= 3) return new ExpelMeasurementDTO(name, lateCount, absentCount, "면담");
        if (expelRiskMeasurement >= 2) return new ExpelMeasurementDTO(name, lateCount, absentCount, "경고");
        return new ExpelMeasurementDTO(name, lateCount, absentCount, null);
    }
    
    public AttendanceResultDTOs getAttendanceResult() {
        int attendCount = 0;
        int lateCount = 0;
        int absentCount = 0;
        List<AttendanceResultDTO> attendanceResults = attendances.stream()
                .map(Attendance::createAttendanceResult)
                .toList();
        
        for (AttendanceResultDTO attendanceResult : attendanceResults) {
            if (attendanceResult.attendanceStatus().equals("출석")) {
                attendCount++;
                continue;
            }
            if (attendanceResult.attendanceStatus().equals("지각")) {
                lateCount++;
                continue;
            }
            if (attendanceResult.attendanceStatus().equals("결석")) {
                absentCount++;
            }
        }
        
        return new AttendanceResultDTOs(name, attendanceResults, attendCount, lateCount, absentCount);
    }
    
    public AttendanceResultDTO attend(LocalDateTime attendDateTime) {
        Attendance newAttendance = new Attendance(attendDateTime);
        attendances.add(newAttendance);
        return newAttendance.createAttendanceResult();
    }
    
    public AttendanceModifyDTO modifyAttendance(LocalDate date, LocalTime time) {
        for (Attendance attendance : attendances) {
            if (attendance.isSameDay(date)) {
                return attendance.modifyAttendanceTime(time);
            }
        }
        
        try {
            Attendance newAttendance = new Attendance(LocalDateTime.of(date, time));
            return new AttendanceModifyDTO(date, null, null, time, newAttendance.createAttendanceResult().attendanceStatus());
        } catch (IllegalAttendDateException e) {
            throw new IllegalAttendDateException("수정 가능한 날짜가 아닙니다.");
        } catch (IllegalAttendTimeException e) {
            throw new IllegalAttendTimeException("수정 가능한 시간이 아닙니다.");
        }
    }
}
