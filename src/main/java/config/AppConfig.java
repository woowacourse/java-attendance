package config;

import controller.AttendanceController;
import domain.Attendance;
import domain.AttendanceSheet;
import domain.policy.AbsentPolicy;
import domain.policy.CampusTimePolicy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public enum AppConfig {
    INSTANCE;

    private static final Path FILE_PATH = Paths.get("src/main/resources/attendances.csv");

    public static final LocalDate TODAY = LocalDate.of(2024, 12, 13);
    public static final int ATTENDANCE_YEAR = 2024;
    public static final int ATTENDANCE_MONTH = 12;

    public AttendanceController createAttendanceController() {
        return new AttendanceController(createInputView(), createOutputView(), createAttendanceSheet());
    }

    private InputView createInputView() {
        return new InputView(new Scanner(System.in));
    }

    private OutputView createOutputView() {
        return new OutputView();
    }

    private AttendanceSheet createAttendanceSheet() {
        ReadFile<Attendance, AttendanceSheet> readFile = new AttendanceSheetFactory(new CampusTimePolicy(),
                new AbsentPolicy());
        return readFile.loadFile(FILE_PATH);
    }
}
