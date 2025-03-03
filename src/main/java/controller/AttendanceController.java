package controller;

import java.time.LocalDate;
import view.OutputView;

public class AttendanceController {
    private final OutputView outputView = new OutputView();

    public void run(LocalDate today) {
        outputView.displayMenu(today);
    }
}
