package attendance.utility;

import java.util.HashMap;
import java.util.Map;

import attendance.domain.AttendanceStatus;
import attendance.domain.SanctionLevel;

public class EnumTextConverter {
    private static final Map<AttendanceStatus, String> ATTENDANCE_STATUS_TEXT = new HashMap<>();
    private static final Map<SanctionLevel, String> SANCTION_LEVEL_TEXT = new HashMap<>();

    static {
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.ABSENCE, "결석");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.LATE, "지각");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.ATTENDANCE, "출석");

        SANCTION_LEVEL_TEXT.put(SanctionLevel.DISMISS, "제적");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.NEED_MEETING, "면담");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.WARNING, "경고");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.NONE, "");
    }

    public static String convertState(AttendanceStatus status) {
        return ATTENDANCE_STATUS_TEXT.get(status);
    }

    public static String convertLevel(SanctionLevel level) {
        return SANCTION_LEVEL_TEXT.get(level);
    }
}
