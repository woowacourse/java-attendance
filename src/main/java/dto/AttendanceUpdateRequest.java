package dto;

public record AttendanceUpdateRequest(
        String nickname,
        String updateDay,
        String updateTime
) {
}
