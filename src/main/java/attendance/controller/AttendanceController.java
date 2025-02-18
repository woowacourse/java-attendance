package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.view.InputView;
import java.time.LocalDateTime;

public class AttendanceController {

    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        Attendances attendances = new AttendancesFactory().initialize();
        LocalDateTime now = LocalDateTime.now();
        Command command = Command.from(inputView.inputCommand(now.toLocalDate()));
    }
}
