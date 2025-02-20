package controller.dto;

import domain.date.AttendanceDateTime;
import domain.attendance.AttendanceType;

public record AttendanceHistoryDto(
        int month,
        int day,
        int dayOfWeek,
        int hour,
        int minute,
        AttendanceType type
) {
    public static AttendanceHistoryDto from(AttendanceDateTime attendanceDateTime) {
        return new AttendanceHistoryDto(
                attendanceDateTime.getMonth(),
                attendanceDateTime.getDay(),
                attendanceDateTime.getDayOfWeek(),
                attendanceDateTime.getHour(),
                attendanceDateTime.getMinute(),
                attendanceDateTime.getAttendanceType()
        );
    }
}
