package controller;

import domain.AttendanceSheet;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private InputView inputView;
    private OutputView outputView;
    private AttendanceSheet attendanceSheet;

    public AttendanceController(InputView inputView, OutputView outputView, AttendanceSheet attendanceSheet) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceSheet = attendanceSheet;
    }

    public void start() {

    }

}
