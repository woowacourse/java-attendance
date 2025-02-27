package attendance.dto;

import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;

import java.util.List;
import java.util.Map;

public record AttendanceCheckDto (
    String name,
    List<AttendanceInfoDto> attendanceInfos,
    Map<AttendanceStatus, Integer> attendanceStatusCount,
    AttendancePenalty penalty
){
    public static AttendanceCheckDto of(String name, List<AttendanceInfoDto> attendanceInfos,
                                     Map<AttendanceStatus, Integer> attendanceStatusCount,
                                     AttendancePenalty penalty) {
        return new AttendanceCheckDto(name, attendanceInfos, attendanceStatusCount, penalty);
    }
}
