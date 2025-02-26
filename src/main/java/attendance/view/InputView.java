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

    public String readNickname() {
        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }
}
