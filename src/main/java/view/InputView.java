package view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public Menu readMenu() {
        String input = scanner.nextLine();
        return Menu.from(input);
    }
}
