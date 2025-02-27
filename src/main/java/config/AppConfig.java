package config;

import controller.AttendanceController;
import domain.Attendance;
import domain.AttendanceSheet;
import domain.policy.AbsentPolicy;
import view.InputView;
import view.OutputView;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum AppConfig {
    INSTANCE;

    private static final Path FILE_PATH = Paths.get("src/main/resources/attendances.csv");

    public AttendanceController createAttendanceController() {
        return new AttendanceController(createInputView(), createOutputView(), createAttendanceSheet());
    }

    private InputView createInputView() {
        return new InputView();
    }

    private OutputView createOutputView() {
        return new OutputView();
    }

    private AttendanceSheet createAttendanceSheet() {
        ReadFile<Attendance, AttendanceSheet> readFile = new AttendanceSheetFactory(new AbsentPolicy());
        return readFile.loadFile(FILE_PATH);
    }
}
