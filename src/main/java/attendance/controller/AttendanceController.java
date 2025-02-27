package attendance.controller;

import java.util.function.BooleanSupplier;

import attendance.service.AttendanceService;
import attendance.util.DateTimeUtil;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AttendanceController {

    private final AttendanceService service = new AttendanceService();

    public void run() {
        while (process(this::menu)) {
        }
    }

    private boolean menu() {
        switch (InputView.menu(DateTimeUtil.nowDate())) {
            case "1" -> {
                process(this::attendance);
            }
            case "2" -> {
                process(this::modifyAttendance);
            }
            case "3" -> {
                process(this::history);
            }
            case "4" -> {
                process(this::riskCrews);
            }
            case "q", "Q" -> {
                return false;
            }
            default -> {
                throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
            }
        }
        return true;
    }

    private void attendance() {
        service.attendance(DateTimeUtil.nowDate(), InputView.attendance());
    }

    private void modifyAttendance() {

    }

    private void history() {

    }

    private void riskCrews() {

    }

    private void process(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            OutputView.exception(e);
            process(action);
        }
    }

    private boolean process(BooleanSupplier action) {
        try {
            return action.getAsBoolean();
        } catch (IllegalArgumentException e) {
            OutputView.exception(e);
            return process(action);
        }
    }
}
