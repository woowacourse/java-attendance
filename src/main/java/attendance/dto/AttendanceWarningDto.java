package attendance.dto;

import attendance.model.AttendanceWarningLevel;
import attendance.model.Nickname;

public record AttendanceWarningDto(
        Nickname nickname,
        int lateCount,
        int absentCount,
        AttendanceWarningLevel attendanceLevel
) {
    public String koreanLabel() {
        return attendanceLevel.getKoreanLabel();
    }
}
