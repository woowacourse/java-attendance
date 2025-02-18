package attendance.controller;

import attendance.AttendancesFactory;
import attendance.model.Attendances;
import attendance.model.Command;
import attendance.view.InputView;
import java.time.LocalDateTime;

public class AttendanceController {

    private final InputView inputView;
    private final Attendances attendances;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
        attendances = new AttendancesFactory().initialize();
    }

    public void run() {
        LocalDateTime now = LocalDateTime.now();
        Command command = Command.from(inputView.inputCommand(now.toLocalDate()));
        try {
            if (command == Command.ATTENDANCE) {
                doAttendance(now);
            }
        } catch (RuntimeException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void doAttendance(LocalDateTime now) {
        String nickname = inputView.inputNickname();
        attendances.validateAttendance(nickname);
    }
}
