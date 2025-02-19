package domain;

import dto.AttendanceResultDTO;
import dto.AttendanceResultDTOs;
import dto.ExpelMeasurementDTO;

import java.time.LocalDateTime;
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
            if (attendanceResult.getAttendanceStatus().equals("지각")) {
                lateCount++;
                continue;
            }
            if (attendanceResult.getAttendanceStatus().equals("결석")) {
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
            if (attendanceResult.getAttendanceStatus().equals("출석")) {
                attendCount++;
                continue;
            }
            if (attendanceResult.getAttendanceStatus().equals("지각")) {
                lateCount++;
                continue;
            }
            if (attendanceResult.getAttendanceStatus().equals("결석")) {
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
}
