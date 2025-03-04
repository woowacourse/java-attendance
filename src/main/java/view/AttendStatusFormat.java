package view;

import domain.AttendStatus;
import java.util.Arrays;

public enum AttendStatusFormat {
    ATTEND(AttendStatus.ATTEND, "출석"),
    LATE(AttendStatus.LATE, "지각"),
    ABSENCE(AttendStatus.ABSENCE, "결석");

    private final AttendStatus attendStatus;
    private final String text;

    AttendStatusFormat(final AttendStatus attendStatus, final String text) {
        this.attendStatus = attendStatus;
        this.text = text;
    }

    public static String findStatusFormat(final AttendStatus attendStatus) {
        return Arrays.stream(AttendStatusFormat.values())
                .filter(attendStatusFormat -> attendStatusFormat.attendStatus == attendStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 상태 formatting 실패"))
                .text;
    }
}
