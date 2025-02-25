import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNickname() {
        return scanner.nextLine();
    }

    public String getTodayLocalDateTime() {
        String time = scanner.nextLine();
        return AttendanceController.TODAY_LOCAL_DATE +" "+ time;
    }

    public Command getCommand() {
        return Command.findCommand(scanner.nextLine());
    }
}
