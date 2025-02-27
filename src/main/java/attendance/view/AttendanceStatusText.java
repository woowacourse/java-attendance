package attendance.view;

import attendance.domain.AttendanceStatus;

public enum AttendanceStatusText {
    ABSENCE("결석"),
    LATE("지각"),
    ATTENDANCE("출석");

    private final String text;

    AttendanceStatusText(String text) {
        this.text = text;
    }

    public static String convert(AttendanceStatus status) {
        if (status.equals(AttendanceStatus.ABSENCE)) {
            return ABSENCE.text;
        }
        if (status.equals(AttendanceStatus.LATE)) {
            return LATE.text;
        }
        return ATTENDANCE.text;
    }

}
