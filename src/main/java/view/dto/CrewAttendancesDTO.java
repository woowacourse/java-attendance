package view.dto;

import domain.AttendanceStatistics;
import domain.Attendances;
import domain.Crew;
import java.util.List;

public record CrewAttendancesDTO(String nickName, List<AttendanceLogDTO> attendanceLogDTOs, int present, int late,
                                 int absent, String alertLevel) {
    public static CrewAttendancesDTO from(Crew crew, AttendanceStatistics attendanceStatistics) {
        Attendances attendances = crew.getAttendances();
        List<AttendanceLogDTO> sortedAttendanceLogDtos = attendances.getAttendanceLog().stream()
                .map(AttendanceLogDTO::from)
                .sorted()
                .toList();
        
        return new CrewAttendancesDTO(crew.getNickname(), sortedAttendanceLogDtos, attendanceStatistics.getPresent(),
                attendanceStatistics.getLate(), attendanceStatistics.getAbsent(),
                attendanceStatistics.getAlertLevel().getName());
    }
}
