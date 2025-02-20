package controller.dto;

public record AttendanceRequestDto(
        int day,
        AttendanceTimeDto attendanceTimeDto,
        String nickname
) {
}
