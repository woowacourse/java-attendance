package attendance.controller;

import attendance.domain.AttendanceBook;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private AttendanceBook attendanceBook;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {

        initAttendanceBook();
    }

    private void initAttendanceBook() {

    }
}
