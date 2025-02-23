package view.dto;

import domain.AttendanceStatus;
import domain.AttendanceStatusCount;
import domain.Crew;
import java.util.List;
import java.util.Map;

public record CrewAttendancesDto(String nickName, List<AttendanceLogDto> attendanceLogDtos, int present, int late,
                                 int absent, String alertLevel) {
    public static CrewAttendancesDto from(Crew crew) {
        AttendanceStatusCount attendanceStatusCount = crew.getAttendanceCount();
        List<AttendanceLogDto> sortedAttendanceLogDtos = crew.getAttendances().stream()
                .map(AttendanceLogDto::from)
                .sorted()
                .toList();
        Map<AttendanceStatus, Integer> statuses = attendanceStatusCount.getStatuses();
        return new CrewAttendancesDto(crew.getNickname(), sortedAttendanceLogDtos,
                statuses.get(AttendanceStatus.PRESENT),
                statuses.get(AttendanceStatus.LATE),
                statuses.get(AttendanceStatus.ABSENT),
                attendanceStatusCount.calculateAttendanceAlertLevel().getName());
    }
}
