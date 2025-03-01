package constant;

public enum OutputViewMessage {

    ATTENDANCE_CHECK_IN_RESPONSE("%02d월 %02d일 %s %02d:%02d (%s)"),
    ATTENDANCE_UPDATE_RESPONSE("%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!"),
    ATTENDANCE_UPDATE_NULL_RESPONSE("%02d월 %02d일 %s --:-- (%s) -> %02d:%02d (%s) 수정 완료!"),
    ATTENDANCE_HISTORY_RESPONSE("%02d월 %02d일 %s %02d:%02d (%s)"),
    ATTENDANCE_HISTORY_NULL_RESPONSE("%02d월 %02d일 %s --:-- (%s)"),
    ATTENDANCE_SUCCESS_TYPE_RESPONSE("출석: %d회%n"),
    ATTENDANCE_BE_LATE_TYPE_RESPONSE("지각: %d회%n"),
    ATTENDANCE_ABSENCE_TYPE_RESPONSE("결석: %d회"),
    ATTENDANCE_PUNISHMENT_TYPE_RESPONSE(" 대상자입니다."),
    ATTENDANCE_RISK_CREWS_RESPONSE("닉네임을 입력해 주세요."),
    ;

    private final String message;

    OutputViewMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
