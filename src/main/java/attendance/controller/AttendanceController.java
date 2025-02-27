package attendance.controller;

import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final AttendanceService attendanceService;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, AttendanceService attendanceService, OutputView outputView) {
        this.inputView = inputView;
        this.attendanceService = attendanceService;
        this.outputView = outputView;
    }

    public void run() {
        attendanceService.readAttendance();
    }
}
