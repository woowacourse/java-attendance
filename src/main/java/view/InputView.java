package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Menu readMenu() {
        String input = scanner.nextLine();
        return Menu.from(input);
    }

    public String readNickname() {
        System.out.printf("%n닉네임을 입력해 주세요.%n");
        return scanner.nextLine();
    }
}
