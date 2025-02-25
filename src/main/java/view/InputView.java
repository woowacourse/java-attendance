package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;
import util.validator.InputValidator;

public class InputView {

    private static final String INPUT_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String INPUT_TIME_MESSAGE = "등교 시간을 입력해 주세요.";
    private static final String INPUT_EDIT_NAME_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String INPUT_EDIT_DAY_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String INPUT_EDIT_TIME_MESSAGE = "언제로 변경하겠습니까?";
    private static final String CHOOSE_FUNCTION_MESSAGE = "오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.%n";
    private static final String FUNCTION_1_MESSAGE = "1. 출석 확인";
    private static final String FUNCTION_2_MESSAGE = "2. 출석 수정";
    private static final String FUNCTION_3_MESSAGE = "3. 크루별 출석 기록 확인";
    private static final String FUNCTION_4_MESSAGE = "4. 제적 위험자 확인";
    private static final String FUNCTION_Q_MESSAGE = "Q. 종료";

    public String readName() {
        String name = basicInput(INPUT_NAME_MESSAGE);
        InputValidator.checkNull(name);
        return name;
    }

    public String readTime() {
        String time = basicInput(INPUT_TIME_MESSAGE);
        InputValidator.checkNull(time);
        return time;
    }

    public String readEditName() {
        String name = basicInput(INPUT_EDIT_NAME_MESSAGE);
        InputValidator.checkNull(name);
        return name;
    }

    public String readEditDayOfMonth() {
        String dayOfMonth = basicInput(INPUT_EDIT_DAY_MESSAGE);
        InputValidator.checkNull(dayOfMonth);
        InputValidator.checkInteger(dayOfMonth);
        return dayOfMonth;
    }

    public String readEditTime() {
        String time = basicInput(INPUT_EDIT_TIME_MESSAGE);
        InputValidator.checkNull(time);
        return time;
    }

    public String readFunction(LocalDate localDate) {
        Scanner sc = new Scanner(System.in);
        System.out.printf(CHOOSE_FUNCTION_MESSAGE,
            localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)
        );
        printMessage(FUNCTION_1_MESSAGE);
        printMessage(FUNCTION_2_MESSAGE);
        printMessage(FUNCTION_3_MESSAGE);
        printMessage(FUNCTION_4_MESSAGE);
        printMessage(FUNCTION_Q_MESSAGE);

        return sc.nextLine();
    }

    private String basicInput(String message) {
        Scanner sc = new Scanner(System.in);
        printMessage(message);
        return sc.nextLine();
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
