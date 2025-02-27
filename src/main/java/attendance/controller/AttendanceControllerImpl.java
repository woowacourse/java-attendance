package attendance.controller;

import attendance.service.AttendanceService;
import attendance.util.DateTimeUtil;
import attendance.util.RetryHandler;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceControllerImpl implements AttendanceController {

    private boolean running = true;
    private final AttendanceService service = new AttendanceService();

    public void run() {
        while (running) {
            RetryHandler.retryIfFailuare(this::menu);
        }
    }

    private void menu() {
        Command command = InputView.menu(DateTimeUtil.nowDate());
        command.run(this);
    }

    @Override
    public void attendance() {
        OutputView.attendanceResponse(
            service.attendance(DateTimeUtil.nowDate(), InputView.attendance()));
    }

    @Override
    public void modifyAttendance() {
        OutputView.modifyAttendanceResponse(
            service.modifyAttendance(InputView.modifyAttendance(DateTimeUtil.nowDate())));
    }

    @Override
    public void attendanceHistory() {
        OutputView.attendanceHistoryResponse(
            service.attendanceHistory(DateTimeUtil.nowDate(), InputView.attendanceHisotory()));
    }

    @Override
    public void riskCrews() {
        OutputView.riskCrewsResponse(
            service.riskCrews(DateTimeUtil.nowDate()));
    }

    @Override
    public void quit() {
        running = false;
    }
}
