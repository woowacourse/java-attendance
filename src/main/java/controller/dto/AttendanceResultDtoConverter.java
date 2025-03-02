package controller.dto;

import domain.attendance.AttendanceResult;
import domain.attendance.AttendanceStatus;
import domain.crew.CrewStatus;
import dto.AttendanceResultDto;
import java.util.Map;

public class AttendanceResultDtoConverter {

    public static AttendanceResultDto toDto(AttendanceResult attendanceResult) {
        Map<AttendanceStatus, Integer> attendanceStatus = attendanceResult.getAttendanceStatus();
        CrewStatus crewStatus = attendanceResult.getCrewStatus();

        return new AttendanceResultDto(
                attendanceStatus.get(AttendanceStatus.ATTEND),
                attendanceStatus.get(AttendanceStatus.LATE),
                attendanceStatus.get(AttendanceStatus.ABSENT),
                crewStatus.getDescription()
        );
    }
}
