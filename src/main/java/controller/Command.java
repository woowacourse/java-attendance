package controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum Command {
    Attend("1", AttendanceController::attend),
    MODIFY_ATTENDANCE("2", AttendanceController::modifyAttendance),
    READ_ATTENDANCE_HISTORY("3", AttendanceController::readAttendanceHistory),
    READ_PENALTY_HISTORY("4", AttendanceController::readPenaltyHistory),
    QUIT("Q", AttendanceController::quit);

    private final String option;
    private final Consumer<AttendanceController> action;

    Command(String option, Consumer<AttendanceController> action) {
        this.option = option;
        this.action = action;
    }

    public static Command getCommand(String value) {
        return Arrays.stream(values())
                .filter((command) -> command.option.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 옵션입니다."));
    }

    public void excute(AttendanceController controller) {
        action.accept(controller);
    }
}
