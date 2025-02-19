package controller;

import domain.CurrentDateGenerator;
import domain.DateGenerator;
import view.InputView;

public class AttendanceController {
    private final InputView inputView = new InputView();
    private final DateGenerator currentDateGenerator = new CurrentDateGenerator();

    public void run() {
        inputView.displayMenu(currentDateGenerator.generate());
    }
}
