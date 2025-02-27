package attendance.controller;

import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;

public class AttendanceController {

    private final InputView inputView;
    private final AttendanceService attendanceService;
    private final OutputView outputView;
    private final DateGenerator dateGenerator;

    public AttendanceController(InputView inputView, AttendanceService attendanceService,
                                OutputView outputView, DateGenerator dateGenerator) {
        this.inputView = inputView;
        this.attendanceService = attendanceService;
        this.outputView = outputView;
        this.dateGenerator = dateGenerator;
    }

    public void run() {
        attendanceService.readAttendance();
        LocalDate today = dateGenerator.generate();
        AttendanceOption option;

        do {
            option = inputView.readAttendanceOption(today);
        } while (option != AttendanceOption.QUIT);
    }
}
