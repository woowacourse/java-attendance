package controller;

import repository.AttendanceRepository;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceRepository attendanceRepository;

    public AttendanceController(InputView inputView, OutputView outputView,
        AttendanceRepository attendanceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceRepository = attendanceRepository;
    }

    public void run() {

    }
}
