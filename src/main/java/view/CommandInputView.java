package view;

import java.time.LocalDate;
import java.util.Scanner;
import util.AttendanceDateTimeFormatter;

public class CommandInputView {

    private static final String LINE = System.lineSeparator();
    private static final String WELCOME_MESSAGE_FORMAT = "오늘은 %s입니다. 기능을 선택해 주세요.";
    private static final String COMMAND_INPUT_GUIDE = ""
            + "1. 출석 확인" + LINE
            + "2. 출석 수정" + LINE
            + "3. 크루별 출석 기록 확인" + LINE
            + "4. 제적 위험자 확인" + LINE
            + "Q. 종료";

    private final Scanner scanner;
    private final InputValidator inputValidator;

    public CommandInputView(final Scanner scanner, final InputValidator inputValidator) {
        this.scanner = scanner;
        this.inputValidator = inputValidator;
    }

    public String getCommand(final LocalDate date) {
        System.out.printf(LINE + WELCOME_MESSAGE_FORMAT + LINE, AttendanceDateTimeFormatter.formatLocalDate(date));
        System.out.println(COMMAND_INPUT_GUIDE);
        return getInput();
    }

    private String getInput() {
        String input = scanner.nextLine();
        inputValidator.validateNullOrEmptyInput(input);
        return input;
    }
}
