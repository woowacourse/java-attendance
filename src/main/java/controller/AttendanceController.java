package controller;

import view.InputView;

public class AttendanceController {
    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        String menuSelection = inputView.readMenuSelection();
    }
}
