package controller.dto;

import domain.AttendanceStatus;
import domain.CrewAttendance;
import domain.Penalty;
import java.util.Map;

public record PenaltyCrewDto(
        String name,
        int absenceCount,
        int perceptionCount,
        String penaltyName
) {
    public static PenaltyCrewDto from(CrewAttendance crewAttendance) {
        Map<AttendanceStatus, Integer> attendanceStatusCount = crewAttendance.calculateAttendanceStatusCount();
        Penalty penalty = crewAttendance.calculatePenalty();

        return new PenaltyCrewDto(
                crewAttendance.getCrew().getName(),
                attendanceStatusCount.get(AttendanceStatus.ABSENCE),
                attendanceStatusCount.get(AttendanceStatus.PERCEPTION),
                penalty.getName()
        );
    }
}
