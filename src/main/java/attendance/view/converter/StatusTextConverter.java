package attendance.view.converter;

import java.util.HashMap;
import java.util.Map;

import attendance.domain.AttendanceStatus;
import attendance.interfaces.Converter;

public class StatusTextConverter implements Converter<AttendanceStatus> {
    private static final Map<AttendanceStatus, String> ATTENDANCE_STATUS_TEXT = new HashMap<>();
    private static final String CANT_CONVERTING = "변환할 수 없는 값입니다: ";

    static {
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.ABSENCE, "결석");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.LATE, "지각");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.ATTENDANCE, "출석");
        ATTENDANCE_STATUS_TEXT.put(AttendanceStatus.TRUANCY, "결석");
    }

    @Override
    public static String convert(AttendanceStatus status) {
        var converted = ATTENDANCE_STATUS_TEXT.get(status);
        if (converted == null) {
            throw new NullPointerException(CANT_CONVERTING + status);
        }
        return converted;
    }
}
