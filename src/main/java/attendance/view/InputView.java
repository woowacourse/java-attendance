package attendance.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputView {

    private Scanner scanner = new Scanner(System.in);

    public OperationCommand readOperationCommand() {
        String commandText = scanner.nextLine();
        return OperationCommand.from(commandText);
    }
}
