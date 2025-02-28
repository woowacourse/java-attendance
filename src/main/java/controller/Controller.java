package controller;

import view.Function;
import view.input.InputView;
import view.output.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        outputView.displayFunctionPrompt();
        Function function = Function.checkFunctionNumber(inputView.askFunctionSelection());

    }
}