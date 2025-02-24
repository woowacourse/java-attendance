package controller.dto;

import domain.AttendanceStatus;
import domain.DateTime;

public record AttendanceRecodeDto(
        DateTime dateTime,
        String attendanceStatusName
) {
    public static AttendanceRecodeDto from(DateTime dateTime) {
        return new AttendanceRecodeDto(dateTime, AttendanceStatus.from(dateTime).getName());
    }
}
