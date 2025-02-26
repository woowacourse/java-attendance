package attendance.view;

import java.time.LocalDate;
import java.util.Scanner;
import attendance.util.TimeFormatter;

public class InputView {

    private static final String LINE = System.lineSeparator();
    private static final String NICKNAME_INPUT_GUIDE = "닉네임을 입력해 주세요.";
    private static final String ATTENDANCE_TIME_INPUT_GUIDE = "등교 시간을 입력해 주세요.";
    private static final String MODIFY_NICKNAME_INPUT_GUIDE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String MODIFY_DAY_INPUT_GUIDE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String MODIFY_TIME_INPUT_GUIDE = "언제로 변경하겠습니까?";
    private static final String TITLE_ATTENDANCE_FORM = """
            오늘은 %s입니다. 기능을 선택해 주세요.
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;

    private final InputValidator inputValidator;

    public InputView(final InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String readCommand(final LocalDate todayDate) {
        System.out.printf(LINE + TITLE_ATTENDANCE_FORM, TimeFormatter.formatDate(todayDate));
        String input = readInput();
        System.out.println();
        return input;
    }

    public String readNickname() {
        System.out.println(NICKNAME_INPUT_GUIDE);
        return readInput();
    }

    public String readAttendanceTime() {
        System.out.println(ATTENDANCE_TIME_INPUT_GUIDE);
        return readInput();
    }

    public String readModifyNickname() {
        System.out.println(MODIFY_NICKNAME_INPUT_GUIDE);
        return readInput();
    }

    public String readModifyTime() {
        System.out.println(MODIFY_TIME_INPUT_GUIDE);
        return readInput();
    }

    public String readModifyDay() {
        System.out.println(MODIFY_DAY_INPUT_GUIDE);
        return readInput();
    }

    private String readInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        inputValidator.validateIsNullOrEmpty(input);
        return input;
    }
}
