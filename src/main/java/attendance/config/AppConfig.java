package attendance.config;

import attendance.domain.AttendanceRecordStorage;
import attendance.domain.AttendanceSystem;
import attendance.domain.AttendanceSystemInitializer;
import attendance.domain.CrewStorage;
import attendance.domain.HolidayChecker;
import attendance.view.InputView;
import attendance.view.OutputView;

public class AppConfig {

    private final InputView inputView;
    private final OutputView outputView;
    private final CrewStorage crewStorage;
    private final AttendanceRecordStorage recordStorage;
    private final HolidayChecker holidayChecker;
    private final AttendanceSystem attendanceSystem;
    private final AttendanceSystemInitializer initializer;

    public AppConfig() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.recordStorage = new AttendanceRecordStorage();
        this.crewStorage = new CrewStorage();
        this.holidayChecker = new HolidayChecker();
        this.attendanceSystem = new AttendanceSystem(crewStorage, recordStorage, holidayChecker);
        this.initializer = new AttendanceSystemInitializer(crewStorage, attendanceSystem);
    }

    public InputView getInputView() {
        return inputView;
    }

    public OutputView getOutputView() {
        return outputView;
    }

    public AttendanceSystem getAttendanceSystem() {
        return attendanceSystem;
    }

    public AttendanceSystemInitializer getInitializer() {
        return initializer;
    }
}
