package view.dto;

import domain.Attendances;
import domain.Crew;
import java.util.List;

public record CrewAttendancesDTO(String nickName, List<AttendanceLogDTO> attendanceLogDTOs, int present, int late,
                                 int absent, String alertLevel) {
    public static CrewAttendancesDTO from(Crew crew) {
        Attendances attendances = crew.getAttendances();
        List<AttendanceLogDTO> sortedAttendanceLogDtos = attendances.getAttendanceLog().stream()
                .map(AttendanceLogDTO::from)
                .sorted()
                .toList();
        return new CrewAttendancesDTO(crew.getNickname(), sortedAttendanceLogDtos, attendances.countPresent(),
                attendances.countLate(), attendances.countAbsent(),
                attendances.calculateAttendanceAlertLevel().getName());
    }
}
