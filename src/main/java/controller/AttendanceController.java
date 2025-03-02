package controller;

import domain.Attendances;
import domain.DateProvider;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateProvider dateProvider;
    private final Attendances attendances;

    public AttendanceController(final InputView inputView, final OutputView outputView,
                                final DateProvider dateProvider, final String filaPath) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.dateProvider = dateProvider;
        this.attendances = FileManager.readFile(filaPath);
    }

    public void run() {
        String command;
        do {
            outputView.printWellComeMessage(dateProvider);
            command = inputView.readOption();
            executeFeature(command);
        } while (isExit(command));
    }

    private void executeFeature(String feature) {
        FeatureType featureType = FeatureType.findBy(feature);
        featureType.execute(this);
    }

    boolean isExit(final String feature) {
        return !FeatureType.isExitType(feature);
    }

    void attendanceCheck() {

    }

    void attendanceUpdate() {

    }

    void attendanceHistory() {

    }

    void dismissalHistory() {

    }

    void exit() {
        outputView.printExit();
    }
}
