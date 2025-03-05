package controller;

import domain.AttendanceCommand;
import java.util.Map;
import util.LoopTemplate;

public class ApplicationController {

    private final AttendanceController attendanceController;
    private final Map<AttendanceCommand, Runnable> commandActions;

    public ApplicationController(final AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
        this.commandActions = Map.of(
                AttendanceCommand.CHECK, attendanceController::checkAttendance,
                AttendanceCommand.MODIFY, attendanceController::modifyAttendance,
                AttendanceCommand.LOOK_UP, attendanceController::lookUpAttendance,
                AttendanceCommand.PENALTY_CHECK, attendanceController::penaltyCheck,
                AttendanceCommand.QUIT, () -> System.exit(0)
        );
    }

    public void run() {
        LoopTemplate.tryCatchLoop(() -> {
            final AttendanceCommand attendanceCommand = attendanceController.requestAttendanceCommand();
            commandActions.get(attendanceCommand).run();
        });
    }
}
