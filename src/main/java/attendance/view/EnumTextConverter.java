package attendance.view;

import java.util.HashMap;
import java.util.Map;

import attendance.domain.AttendanceStatus;
import attendance.domain.SanctionLevel;

public class EnumTextConverter {
    private static final Map<AttendanceStatus, String> ATTENDANCE_STATUS_TEXT = new HashMap<>();
    private static final Map<SanctionLevel, String> SANCTION_LEVEL_TEXT = new HashMap<>();
    public static final String CANT_CONVERTING = "변환할 수 없는 값입니다: ";

    static {
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.ABSENCE, "결석");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.LATE, "지각");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.ATTENDANCE, "출석");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.TRUANCY, "결석");

        SANCTION_LEVEL_TEXT.put(SanctionLevel.DISMISS, "제적");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.NEED_MEETING, "면담");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.WARNING, "경고");
        SANCTION_LEVEL_TEXT.put(SanctionLevel.NONE, "");
    }

    public static String convertState(AttendanceStatus status) {
        var converted = ATTENDANCE_STATUS_TEXT.get(status);
        if (converted == null) {
            throw new NullPointerException(CANT_CONVERTING + status);
        }
        return converted;
    }

    public static String convertLevel(SanctionLevel level) {
        var converted = SANCTION_LEVEL_TEXT.get(level);
        if (converted == null) {
            throw new NullPointerException(CANT_CONVERTING + level);
        }
        return converted;
    }
}
