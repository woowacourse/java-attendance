package view;

import java.util.Scanner;

public class InputView {

    private final String INPUT_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    private final String INPUT_TIME_MESSAGE = "등교 시간을 입력해 주세요.";
    private final String INPUT_EDIT_NAME_MESSAGE ="출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final String INPUT_EDIT_DAY_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private final String INPUT_EDIT_TIME_MESSAGE = "언제로 변경하겠습니까?";

    public String readName() {
        return basicInput(INPUT_NAME_MESSAGE);
    }

    public String readTime() {
        return basicInput(INPUT_TIME_MESSAGE);
    }

    public String readEditName() {
        return basicInput(INPUT_EDIT_NAME_MESSAGE);
    }

    public String readEditDayOfMonth(){
        return basicInput(INPUT_EDIT_DAY_MESSAGE);
    }

    public String readEditTime(){
        return basicInput(INPUT_EDIT_TIME_MESSAGE);
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
