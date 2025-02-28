package controller;

import java.util.Arrays;
import java.util.function.Consumer;
import util.ExceptionHandler;

public enum Command {
    SAVE_ATTENDANCE_RECORD("1", AttendanceController::saveAttendanceRecord),
    MODIFY_ATTENDANCE_RECORD("2", AttendanceController::modifyAttendanceRecord),
    PRINT_MONTH_ATTENDANCE_STATISTICS("3", AttendanceController::printMonthAttendanceStatistics),
    PRINT_RISK_CREWS("4", AttendanceController::printRiskCrews),
    QUIT("Q", AttendanceController::quit),
    QUIT_SMALL_CASE("q", AttendanceController::quit),
    NONE("", AttendanceController::printRetryMessage),
    ;

    private final String command;
    private final Consumer<AttendanceController> action;

    Command(String command, Consumer<AttendanceController> action) {
        this.command = command;
        this.action = action;
    }

    public static Command from(String option) {
        return Arrays.stream(values())
                .filter(command -> option.equals(command.command))
                .findAny()
                .orElse(NONE);
    }

    public void run(AttendanceController controller) {
        ExceptionHandler.printErrorMessageWithoutExitProgram(() -> action.accept(controller));
    }
}
