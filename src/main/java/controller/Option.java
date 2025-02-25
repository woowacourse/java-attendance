package controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum Option {
    ATTEND("1", AttendanceController::processAttendance),
    EDIT_ATTENDANCE("2", AttendanceController::processAttendanceEdit),
    SHOW_ATTENDANCE_HISTORY("3", AttendanceController::processAttendanceHistory),
    SHOW_PENALTY_CREWS("4", AttendanceController::processPenaltyCheck),
    EXIT("Q", AttendanceController::exitApplication);
    private final String value;
    private final Consumer<AttendanceController> action;

    Option(String value, Consumer<AttendanceController> action) {
        this.value = value;
        this.action = action;
    }

    public static Option validateValue(String value) {
        return Arrays.stream(values())
                .filter(option -> option.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 옵션입니다."));
    }

    public void execute(AttendanceController controller) {
        action.accept(controller);
    }
}
