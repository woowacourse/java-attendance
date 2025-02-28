package attendance.configuration;

import attendance.domain.AttendanceSystem;
import attendance.domain.checker.AttendanceTypeChecker;
import attendance.domain.checker.HolidayChecker;
import attendance.domain.crew.CrewStorage;
import attendance.domain.initializer.AttendanceSystemInitializer;
import attendance.domain.record.AttendanceRecordStorage;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.Scanner;

public class ApplicationConfiguration {

    private final CrewStorage crewStorage;
    private final HolidayChecker holidayChecker;
    private final AttendanceTypeChecker attendanceTypeChecker;
    private final AttendanceRecordStorage recordStorage;
    private final AttendanceSystem attendanceSystem;
    private final AttendanceSystemInitializer initializer;
    private final Scanner scanner;
    private final InputView inputView;
    private final OutputView outputView;

    public ApplicationConfiguration() {
        this.crewStorage = new CrewStorage();
        this.holidayChecker = new HolidayChecker();
        this.attendanceTypeChecker = new AttendanceTypeChecker(holidayChecker);
        this.recordStorage = new AttendanceRecordStorage();
        this.attendanceSystem = new AttendanceSystem(crewStorage, attendanceTypeChecker, recordStorage);
        this.initializer = new AttendanceSystemInitializer(crewStorage, attendanceSystem);
        this.scanner = new Scanner(System.in);
        this.inputView = new InputView(scanner);
        this.outputView = new OutputView();
    }

    public AttendanceSystem getAttendanceSystem() {
        return attendanceSystem;
    }

    public AttendanceSystemInitializer getInitializer() {
        return initializer;
    }

    public InputView getInputView() {
        return inputView;
    }

    public OutputView getOutputView() {
        return outputView;
    }
}
