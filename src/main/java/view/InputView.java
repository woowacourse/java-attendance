package view;

import java.util.Scanner;

public class InputView {
    public static final String MENU_REGEX = "[1234Qq]";

    private final Scanner scanner = new Scanner(System.in);

    public String readMenu() {
        String input = scanner.nextLine();
        validateMenuInput(input);
        return input;
    }

    private void validateMenuInput(String input) {
        if (!input.matches(MENU_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 메뉴입니다.");
        }
    }
}
