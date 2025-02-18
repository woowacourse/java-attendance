package domain;

import dto.AttendanceResultDTO;
import dto.AttendanceResultDTOs;

import java.util.List;

public class MemberAttendances {
    
    private String name;
    private List<Attendance> attendances;
    
    public MemberAttendances(String name, List<Attendance> attendances) {
        this.name = name;
        this.attendances = attendances;
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
}
