package controller.dto;

import domain.attendance.AttendanceLog;
import dto.AttendanceLogDto;

public class AttendanceLogDtoConverter {

    public static AttendanceLogDto toDto(AttendanceLog attendanceLog) {
        return new AttendanceLogDto(
                attendanceLog.getAttendanceDate(),
                attendanceLog.getAttendanceTime(),
                attendanceLog.getAttendanceStatus());
    }
}
