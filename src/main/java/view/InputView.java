package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final String INPUT_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String INPUT_TIME_MESSAGE = "등교 시간을 입력해 주세요.";
    private static final String INPUT_EDIT_NAME_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String INPUT_EDIT_DAY_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String INPUT_EDIT_TIME_MESSAGE = "언제로 변경하겠습니까?";
    private static final String FUNCTION_MESSAGE = "오늘은 %s입니다. 기능을 선택해 주세요.%n";
    private static final String DATE_FORMAT = "%d월 %02d일 %s";
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int LAST_DAY_OF_MONTH = 31;


    private final Scanner sc = new Scanner(System.in);

    public String readAttendName() {
        return basicInput(INPUT_NAME_MESSAGE);
    }

    public String readTime() {
        return basicInput(INPUT_TIME_MESSAGE);
    }

    public String readEditName() {
        return basicInput(INPUT_EDIT_NAME_MESSAGE);
    }

    public String readEditDayOfMonth() {
        String input = basicInput(INPUT_EDIT_DAY_MESSAGE);
        validateNumber(input);
        validateDayOfMonth(input);
        return input;
    }

    public String readEditTime() {
        return basicInput(INPUT_EDIT_TIME_MESSAGE);
    }

    public String printFunction(LocalDate localDate) {
        String date = dateFormatting(localDate);
        System.out.printf(FUNCTION_MESSAGE, date);
        System.out.printf(
                "1. 출석 확인%n"
                        + "2. 출석 수정%n"
                        + "3. 크루별 출석 기록 확인%n"
                        + "4. 제적 위험자 확인%n"
                        + "Q. 종료%n"
        );

        String input = sc.nextLine();
        validateInput(input);
        return input;
    }

    private String basicInput(String message) {
        printMessage(message);
        String input = sc.nextLine();
        validateInput(input);
        return input;
    }

    private void validateInput(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("내용을 입력해 주세요.");
        }
    }

    private void validateNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
        }
    }

    private void validateDayOfMonth(String input) {
        int parsedNumber = Integer.parseInt(input);

        if (parsedNumber < FIRST_DAY_OF_MONTH || parsedNumber > LAST_DAY_OF_MONTH) {
            throw new IllegalArgumentException("날짜가 유효하지 않습니다.");
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private String dateFormatting(LocalDate localDate) {
        return String.format(DATE_FORMAT, localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
