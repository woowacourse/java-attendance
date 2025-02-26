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

        // count() 계산 메서드 같은거 말고, get으로 가져오기?
        // 그럼 attendance에서 지각, 출석, 결석, 현재 출석 상태를 상태로 가지고 있기?
        // 그럼 필드변수가 너무 많아지는데 괜찮?
        return new CrewAttendancesDTO(crew.getNickname(), sortedAttendanceLogDtos, attendanceStatistics.getPresent(),
                attendanceStatistics.getLate(), attendanceStatistics.getAbsent(),
                attendanceStatistics.getAlertLevel().getName());
    }
}
