package attendance.view;

import attendance.controller.MenuCommand;
import attendance.view.message.InputMessage;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public MenuCommand readMenuCommand() {
        System.out.println(InputMessage.MENU.getContent());
        String input = scanner.nextLine();
        return MenuCommand.parse(input);
    }
}
