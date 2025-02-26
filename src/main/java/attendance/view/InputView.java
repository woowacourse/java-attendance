package attendance.view;

import attendance.model.Command;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public String readCommand(List<Command> commands) {
        System.out.println("기능을 선택해 주세요.");
        printCommands(commands);
        return scanner.nextLine();
    }

    private void printCommands(List<Command> commands) {
        for (Command command : commands) {
            System.out.printf("%s. %s%n", command.getCode(), command.getDescription());
        }
    }
}
