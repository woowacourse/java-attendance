package controller;

import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView = new OutputView();
    private final InputView inputView = new InputView();

    public void run(LocalDate today) {
        outputView.displayMenu(today);
        inputView.readMenu();
    }
}
