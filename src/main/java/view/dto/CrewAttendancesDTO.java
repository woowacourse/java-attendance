package view.dto;

import domain.AttendanceStatus;
import domain.AttendanceStatusCount;
import domain.Crew;
import java.util.List;
import java.util.Map;

public record CrewAttendancesDTO(String nickName, List<AttendanceLogDTO> attendanceLogDTOs, int present, int late,
                                 int absent, String alertLevel) {
    public static CrewAttendancesDTO from(Crew crew) {
        AttendanceStatusCount attendanceStatusCount = crew.getAttendanceCount();
        List<AttendanceLogDTO> sortedAttendanceLogDtos = crew.getAttendances().stream()
                .map(AttendanceLogDTO::from)
                .sorted()
                .toList();
        Map<AttendanceStatus, Integer> statuses = attendanceStatusCount.getStatuses();
        return new CrewAttendancesDTO(crew.getNickname(), sortedAttendanceLogDtos,
                statuses.get(AttendanceStatus.PRESENT),
                statuses.get(AttendanceStatus.LATE),
                statuses.get(AttendanceStatus.ABSENT),
                attendanceStatusCount.calculateAttendanceAlertLevel().getName());
    }
}
