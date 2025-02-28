package view.input;

import static view.input.InputPrompt.GUIDE_FUNCTION_INPUT;

import java.util.Scanner;
import view.output.OutputView;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String askFunctionSelection() {
        System.out.println(GUIDE_FUNCTION_INPUT.getFormat());
        return getUserInput();
    }

    public String getUserInput() {
        String response = scanner.nextLine();
        OutputView.displaySpacing();
        return response;
    }
}