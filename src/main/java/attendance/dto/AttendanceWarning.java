package attendance.dto;

import attendance.model.AttendanceWarningLevel;
import attendance.model.Nickname;

public record AttendanceWarning(
        Nickname nickname,
        int lateCount,
        int absentCount
) {
    public boolean isNotClean() {
        return getAttendanceLevel() != AttendanceWarningLevel.CLEAN;
    }

    public String koreanLabel() {
        return getAttendanceLevel().getKoreanLabel();
    }

    public AttendanceWarningLevel getAttendanceLevel() {
        return AttendanceWarningLevel.determine(lateCount, absentCount);
    }
}
