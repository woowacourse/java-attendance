package view;

import domain.AttendStatus;
import java.util.Arrays;

public enum AttendMessage {

    ATTEND("출석", AttendStatus.ATTEND),
    LATE("지각", AttendStatus.LATE),
    ABSENCE("결석", AttendStatus.ABSENCE);

    private final String message;
    private final AttendStatus attendStatus;

    AttendMessage(String message, AttendStatus attendStatus) {
        this.message = message;
        this.attendStatus = attendStatus;
    }

    public static String formatAttendStatus(AttendStatus attendStatus) {
        return Arrays.stream(AttendMessage.values())
                .filter(attendMessage -> attendMessage.attendStatus == attendStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 상태 판정 실패"))
                .getMessage();
    }

    public String getMessage() {
        return message;
    }
}
