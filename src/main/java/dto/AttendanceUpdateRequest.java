package dto;

public record AttendanceUpdateRequest(
        String nickname,
        String day,
        String updateTime
) {
}
