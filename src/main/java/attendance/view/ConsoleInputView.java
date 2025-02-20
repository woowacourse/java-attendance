package attendance.view;

import java.util.Scanner;

public class ConsoleInputView {
    private final Scanner scanner = new Scanner(System.in);

    public String input() {
        return scanner.nextLine();
    }
}
