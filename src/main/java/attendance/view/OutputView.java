package attendance.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    private final static String METHOD_MESSAGE = """
        1. 출석 확인
        2. 출석 수정
        3. 크루별 출석 기록 확인
        4. 제적 위험자 확인
        Q. 종료""";
    private final static String REQUEST_METHOD = "\n오늘은 MM월 d일 E요일입니다. 기능을 선택해 주세요.";
    private final static String REQUEST_NICKNAME = "닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";

    private static final String ATTENDANCE_FORMAT = "\nMM월 dd일 E요일 HH:mm (%s)";
    private static final String FORMAT_ATTENDANCE_NOT_EXISTING = "\nMM월 dd일 E요일 --:-- (결석) ";
    private static final String MODIFY_TIME_FORM = "-> HH:mm (%s)";

    private static final String INITIALIZE_HISTORY = "\n이번 달 %s의 출석 기록입니다.";
    private static final String FORMAT_STATE = "\n%s: %d회";
    private static final String SANCTION_LEVEL = "\n%s 대상자입니다.\n";

    private static final String INITIALIZE_SANCTION_STATISTIC = "\n제적 위험자 조회 결과";
    private static final String FORMAT_SANCTION = "\n- %s: 결석 %d회, 지각 %d회 (%s)";

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        print(error);
    }

    public void printRequestMessage(LocalDateTime dateTime) {
        var formatted = DateTimeFormatter.ofPattern(REQUEST_METHOD).format(dateTime);
        print(formatted);
    }

    public void printMethod() {
        print(METHOD_MESSAGE);
    }

    public void printRequestNickName() {
        print(REQUEST_NICKNAME);
    }

    public void printRequestAttendanceTime() {
        print(REQUEST_ATTENDANCE_TIME);
    }

    public void printAttendance(LocalDateTime dateTime, String state) {
        var format = String.format(ATTENDANCE_FORMAT, state);
        var formatted = DateTimeFormatter.ofPattern(format).format(dateTime);
        print(formatted);
    }
}
