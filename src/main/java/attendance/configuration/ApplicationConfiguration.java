package attendance.configuration;

import attendance.domain.AttendanceSystem;
import attendance.domain.checker.AttendanceChecker;
import attendance.domain.checker.HolidayChecker;
import attendance.domain.crew.CrewStorage;
import attendance.domain.initializer.AttendanceSystemInitializer;
import attendance.view.InputView;
import java.util.Scanner;

public class ApplicationConfiguration {

    private final CrewStorage crewStorage;
    private final HolidayChecker holidayChecker;
    private final AttendanceChecker attendanceChecker;
    private final AttendanceSystem attendanceSystem;
    private final AttendanceSystemInitializer initializer;
    private final Scanner scanner;
    private final InputView inputView;

    public ApplicationConfiguration() {
        this.crewStorage = new CrewStorage();
        this.holidayChecker = new HolidayChecker();
        this.attendanceChecker = new AttendanceChecker(holidayChecker);
        this.attendanceSystem = new AttendanceSystem(crewStorage, attendanceChecker);
        this.initializer = new AttendanceSystemInitializer(crewStorage, attendanceSystem);
        this.scanner = new Scanner(System.in);
        this.inputView = new InputView(scanner);
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
}
