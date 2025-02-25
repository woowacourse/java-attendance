package controller;

import java.util.Arrays;
import java.util.function.Consumer;

public enum FeatureType {

    ATTENDANCE_CHECK("1", MainController::attendanceCheck),
    ATTENDANCE_UPDATE("2", MainController::attendanceUpdate),
    ATTENDANCE_RECORD("3", MainController::attendanceRecord),
    READ_ABSENCE("4", MainController::readAbsence),
    EXIT("Q", MainController::exit);

    private final String command;
    private final Consumer<MainController> feature;

    FeatureType(final String command, final Consumer<MainController> feature) {
        this.command = command;
        this.feature = feature;
    }

    public static FeatureType findBy(final String feature) {
        return Arrays.stream(values())
                .filter(value -> value.command.equals(feature))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기능입니다."));
    }

    public static boolean isExitType(final String feature) {
        return EXIT.command.equals(feature);
    }

    public void execute(MainController mainController) {
        feature.accept(mainController);
    }
}
