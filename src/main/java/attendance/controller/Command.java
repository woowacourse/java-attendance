package attendance.controller;

import java.util.Arrays;
import java.util.function.Consumer;

import attendance.exception.ExceptionMessage;
import attendance.util.RetryHandler;

public enum Command {
    ATTENDANCE("1", AttendanceController::attendance),
    MODIFY_ATTENDANCE("2", AttendanceController::modifyAttendance),
    ATTENDANCE_HISTORY("3", AttendanceController::attendanceHistory),
    RISK_CREWS("4", AttendanceController::riskCrews),
    QUIT("Q", AttendanceController::quit),
    ;

    private final String input;
    private final Consumer<AttendanceController> action;

    Command(String input, Consumer<AttendanceController> action) {
        this.input = input;
        this.action = action;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
            .filter(command -> command.input.equalsIgnoreCase(input))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.INVALID_COMMAND.getMessage(input)));
    }

    public void run(AttendanceController controller) {
        RetryHandler.retryIfFailuare(() -> action.accept(controller));
    }
}
