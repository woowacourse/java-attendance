package attendance.view;

import java.time.LocalDate;

import attendance.common.utill.DateTimeFormatterWrapper;

public class OutputView {

    private final String METHOD = "1. 출석 확인\n"
        + "2. 출석 수정\n"
        + "3. 크루별 출석 기록 확인\n"
        + "4. 제적 위험자 확인\n"
        + "Q. 종료";
    private final static String INPUT_NICKNAME = "닉네임을 입력해 주세요.";
    private final String INPUT_ATTENDANCE_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final String INPUT_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private final String INPUT_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";

    public void printMethod() {
        println(DateTimeFormatterWrapper.formattingToday(LocalDate.now()));
        println(METHOD);
    }

    public void println(String string) {
        System.out.println(string);
    }

    public void printError(String message) {
        println(message);
    }

    public void printAttendanceModifyNicknameInput() {
        println(INPUT_ATTENDANCE_MODIFY_NICKNAME);
    }

    public void printAttendanceModifyDateInput() {
        println(INPUT_ATTENDANCE_MODIFY_DATE);
    }

    public void printNicknameInput() {
        println(INPUT_NICKNAME);
    }

    public void printAttendanceTimeInput() {
        println(INPUT_ATTENDANCE_TIME);
    }
}
