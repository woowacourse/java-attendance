package attendance.view;

import java.util.Scanner;

public class InputView {

    private static final String NICKNAME_INPUT_MESSAGE = "닉네임을 입력해 주세요.";
    private final Scanner scanner = new Scanner(System.in);

    private InputView() {}

    public static InputView create() {
        return new InputView();
    }

    public String inputNickname() {
        printMessage(NICKNAME_INPUT_MESSAGE);
        return userInput();
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private String userInput() {
        return scanner.nextLine();
    }
}
