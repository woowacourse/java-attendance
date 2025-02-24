package controller;

import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView;
    private final InputView inputView;

    public AttendanceController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        String functionChoice = inputView.readFunctionChoice();
        if (functionChoice.equals("1")) {
            doCheckService();
        }
    }

    private void doCheckService() {

    }
}
