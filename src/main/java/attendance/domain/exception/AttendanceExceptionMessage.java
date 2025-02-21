package attendance.domain.exception;

public class AttendanceExceptionMessage {
    private static final String PREFIX = "[ERROR] ";

    public static final String ALREADY_ATTENDANCE = PREFIX + "이미 오늘 출석을 하셨습니다. 출석 수정을 이용해주세요.";
    public static final String NOT_IN_ATTENDANCE = PREFIX + " 출석 기록이 없습니다.";
}
