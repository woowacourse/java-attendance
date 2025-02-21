package view;

import static util.constant.InputMessage.CHOOSE_FUNCTION_MESSAGE;
import static util.constant.InputMessage.FUNCTION_1_MESSAGE;
import static util.constant.InputMessage.FUNCTION_2_MESSAGE;
import static util.constant.InputMessage.FUNCTION_3_MESSAGE;
import static util.constant.InputMessage.FUNCTION_4_MESSAGE;
import static util.constant.InputMessage.FUNCTION_Q_MESSAGE;
import static util.constant.InputMessage.INPUT_EDIT_DAY_MESSAGE;
import static util.constant.InputMessage.INPUT_EDIT_NAME_MESSAGE;
import static util.constant.InputMessage.INPUT_EDIT_TIME_MESSAGE;
import static util.constant.InputMessage.INPUT_NAME_MESSAGE;
import static util.constant.InputMessage.INPUT_TIME_MESSAGE;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

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
        System.out.printf(CHOOSE_FUNCTION_MESSAGE, localDate.getMonthValue(),
            localDate.getDayOfMonth(),
            localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));

        System.out.println(FUNCTION_1_MESSAGE);
        System.out.println(FUNCTION_2_MESSAGE);
        System.out.println(FUNCTION_3_MESSAGE);
        System.out.println(FUNCTION_4_MESSAGE);
        System.out.println(FUNCTION_Q_MESSAGE);

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
}
