package controller;

import domain.CurrentDateGenerator;
import domain.DateGenerator;
import view.InputView;

public class AttendanceController {
    private final InputView inputView = new InputView();
    private final DateGenerator currentDateGenerator = new CurrentDateGenerator();

    public void run() {
        String menuInput = inputView.readMenu(currentDateGenerator.generate());
        if (menuInput.matches("[Qq]")) {
            return;
        }
    }
}
