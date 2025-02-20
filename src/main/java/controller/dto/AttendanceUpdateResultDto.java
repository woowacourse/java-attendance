package controller.dto;

import domain.date.AttendanceDateTime;

public record AttendanceUpdateResultDto(AttendanceHistoryDto beforeHistoryDto, AttendanceHistoryDto afterHistoryDto) {
    public static AttendanceUpdateResultDto from(
            AttendanceDateTime beforeAttendanceHistory,
            AttendanceDateTime afterAttendanceHistory) {
        return new AttendanceUpdateResultDto(
                AttendanceHistoryDto.from(beforeAttendanceHistory),
                AttendanceHistoryDto.from(afterAttendanceHistory));
    }
}
