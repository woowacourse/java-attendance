package view;

import java.util.Scanner;

public class InputView {

    private final Scanner inputReader = new Scanner(System.in);

    public InputMethod inputMethod() {
        String inputMethod = input();
        return InputMethod.from(inputMethod);
    }

    public String input() {
        return inputReader.nextLine();
    }
}
