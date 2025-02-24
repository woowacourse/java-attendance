package controller.dto;

import domain.AttendanceStatus;
import domain.WorkDateTime;

public record AttendanceRecodeDto(
        WorkDateTime workDateTime,
        String attendanceStatusName
) {
    public static AttendanceRecodeDto from(WorkDateTime workDateTime) {
        return new AttendanceRecodeDto(workDateTime, AttendanceStatus.from(workDateTime).getName());
    }
}
