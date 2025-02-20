package view;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private final String INPUT_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    private final String INPUT_TIME_MESSAGE = "등교 시간을 입력해 주세요.";
    private final String INPUT_EDIT_NAME_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final String INPUT_EDIT_DAY_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private final String INPUT_EDIT_TIME_MESSAGE = "언제로 변경하겠습니까?";
    private final String FUNCTION_MESSAGE = "오늘은 %s입니다. 기능을 선택해 주세요.%n";
    private final String DATE_FORMAT = "%d월 %02d일 %s";

    public String readName() {
        return basicInput(INPUT_NAME_MESSAGE);
    }

    public String readTime() {
        return basicInput(INPUT_TIME_MESSAGE);
    }

    public String readEditName() {
        return basicInput(INPUT_EDIT_NAME_MESSAGE);
    }

    public String readEditDayOfMonth() {
        return basicInput(INPUT_EDIT_DAY_MESSAGE);
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

        Scanner sc = new Scanner(System.in);
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

    private String dateFormatting(LocalDate localDate) {
        return String.format(DATE_FORMAT, localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
    }
}
