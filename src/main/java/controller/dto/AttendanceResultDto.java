package controller.dto;

import domain.AttendanceStatus;
import domain.CrewAttendance;
import domain.Penalty;
import java.util.Map;

public record AttendanceResultDto(
        String name,
        int attendanceCount,
        int absenceCount,
        int perceptionCount,
        String penaltyName
) {
    public static AttendanceResultDto from(CrewAttendance crewAttendance) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = crewAttendance.calculateAttendanceStatusCount();
        Penalty penalty = crewAttendance.calculatePenalty();

        return new AttendanceResultDto(
                crewAttendance.getCrew().getName(),
                attendanceStatusCount.get(AttendanceStatus.ATTENDANCE),
                attendanceStatusCount.get(AttendanceStatus.ABSENCE),
                attendanceStatusCount.get(AttendanceStatus.PERCEPTION),
                penalty.getName()
        );
    }
}
