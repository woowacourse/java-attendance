package dto;

public record AttendanceCheckInRequest(
        String nickname,
        String checkInTime
) {
}
