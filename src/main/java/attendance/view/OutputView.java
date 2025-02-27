package attendance.view;

import java.time.LocalDateTime;

import attendance.utility.DateTimeFormatterWrapper;

public class OutputView {

    private final static String METHOD_MESSAGE = """
        1. 출석 확인
        2. 출석 수정
        3. 크루별 출석 기록 확인
        4. 제적 위험자 확인
        Q. 종료""";
    private final static String REQUEST_METHOD = "오늘은 MM월 d일 E요일입니다. 기능을 선택해 주세요.";
    private final static String REQUEST_NICKNAME = "닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";

    public void println(String string) {
        System.out.println(string);
    }

    public void printError(String message) {
        println(message);
    }

    public void printRequestMessage(LocalDateTime date) {
        String message = DateTimeFormatterWrapper.getFormatter(REQUEST_METHOD).format(date);
        println(message);
    }

    public void printMethod() {
        println(METHOD_MESSAGE);
    }

    public void printRequestNickname() {
        println(REQUEST_NICKNAME);
    }

    public void printRequestNicknameForModify() {
        println(REQUEST_ATTENDANCE_MODIFY_NICKNAME);
    }

    public void printRequestTime() {
        println(REQUEST_ATTENDANCE_TIME);
    }

    public void printRequestDate() {
        println(REQUEST_ATTENDANCE_MODIFY_DATE);
    }

}
