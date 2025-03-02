package controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum FeatureType {

    ATTENDANCE_CHECK("1", AttendanceController::attendanceCheck),
    ATTENDANCE_UPDATE("2", AttendanceController::attendanceUpdate),
    ATTENDANCE_RECORD("3", AttendanceController::attendanceHistory),
    READ_DISMISSAL("4", AttendanceController::dismissalHistory),
    EXIT("Q", AttendanceController::exit);

    private final String command;
    private final Consumer<AttendanceController> feature;

    FeatureType(final String command, final Consumer<AttendanceController> feature) {
        this.command = command;
        this.feature = feature;
    }

    public static FeatureType findBy(final String command) {
        return Arrays.stream(values())
                .filter(value -> value.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기능입니다."));
    }

    public static boolean isExitType(final String command) {
        return EXIT.command.equals(command);
    }

    public void execute(AttendanceController attendanceController) {
        feature.accept(attendanceController);
    }
}
