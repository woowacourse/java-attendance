package view.dto;

import domain.AttendanceCount;
import domain.Crew;
import java.util.List;

public record CrewAttendancesDTO(String nickName, List<AttendanceLogDTO> attendanceLogDTOs, int present, int late,
                                 int absent, String alertLevel) {
    public static CrewAttendancesDTO from(Crew crew) {
        AttendanceCount attendanceCount = crew.getAttendanceCount();
        List<AttendanceLogDTO> sortedAttendanceLogDtos = crew.getAttendances().stream()
                .map(AttendanceLogDTO::from)
                .sorted()
                .toList();
        return new CrewAttendancesDTO(crew.getNickname(), sortedAttendanceLogDtos, attendanceCount.getPresent(),
                attendanceCount.getLate(), attendanceCount.getAbsent(),
                attendanceCount.calculateAttendanceAlertLevel().getName());
    }
}
