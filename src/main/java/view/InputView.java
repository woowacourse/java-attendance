package view;

import java.util.Scanner;

public class InputView {

    private static final String ATTENDANCE_CHECK_CREW_NICKNAME_INPUT_GUIDE = "닉네임을 입력해 주세요.";
    private static final String ATTENDANCE_CHECK_TIME_INPUT_GUIDE = "등교 시간을 입력해 주세요.";
    private static final String ATTENDANCE_MODIFY_CREW_NICKNAME_INPUT_GUIDE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String ATTENDANCE_MODIFY_DATE_INPUT_GUIDE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String ATTENDANCE_MODIFY_TIME_INPUT_GUIDE = "언제로 변경하겠습니까?";

    private final Scanner scanner;
    private final InputValidator inputValidator;

    public InputView(final Scanner scanner, final InputValidator inputValidator) {
        this.scanner = scanner;
        this.inputValidator = inputValidator;
    }

    public String getAttendanceCheckCrewNickname() {
        System.out.println();
        System.out.println(ATTENDANCE_CHECK_CREW_NICKNAME_INPUT_GUIDE);
        return getInput();
    }

    public String getAttendanceCheckTime() {
        System.out.println(ATTENDANCE_CHECK_TIME_INPUT_GUIDE);
        return getInput();
    }

    public String getAttendanceModifyCrewNickname() {
        System.out.println();
        System.out.println(ATTENDANCE_MODIFY_CREW_NICKNAME_INPUT_GUIDE);
        return getInput();
    }

    public String getAttendanceModifyDayOfMonth() {
        System.out.println(ATTENDANCE_MODIFY_DATE_INPUT_GUIDE);
        return getInput();
    }

    public String getAttendanceModifyTime() {
        System.out.println(ATTENDANCE_MODIFY_TIME_INPUT_GUIDE);
        return getInput();
    }

    public String getAttendanceHistoryCrewNickname() {
        System.out.println();
        System.out.println(ATTENDANCE_CHECK_CREW_NICKNAME_INPUT_GUIDE);
        return getInput();
    }

    private String getInput() {
        String input = scanner.nextLine();
        inputValidator.validateNullOrEmptyInput(input);
        return input;
    }
}
