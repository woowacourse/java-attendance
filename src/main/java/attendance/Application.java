package attendance;

import attendance.controller.Controller;
import attendance.model.Crews;
import attendance.model.FixedCustomClock;
import attendance.model.loader.AttendanceLoader;
import attendance.model.loader.AttendanceProcessor;
import attendance.model.loader.CrewRegistry;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

public class Application {

    public static void main(String[] args) {
        Crews crews = new Crews(new ArrayList<>());
        FixedCustomClock fixedCustomClock = createCustomClock();
        AttendanceLoader attendanceLoader = new AttendanceLoader();
        CrewRegistry crewRegistry = new CrewRegistry(crews);
        loadAttendance(attendanceLoader, fixedCustomClock, crewRegistry);

        InputView inputView = new InputView(fixedCustomClock);
        OutputView outputView = new OutputView();
        Controller controller = new Controller(inputView, outputView, fixedCustomClock, crews);
        controller.run();
    }

    private static FixedCustomClock createCustomClock() {
        Set<LocalDate> holidays = Set.of(
                LocalDate.of(2024, 12, 25)
        );
        return new FixedCustomClock(holidays);
    }

    private static void loadAttendance(AttendanceLoader attendanceLoader, FixedCustomClock fixedCustomClock,
                                       CrewRegistry crewRegistry) {
        AttendanceProcessor attendanceProcessor = new AttendanceProcessor(
                attendanceLoader, fixedCustomClock, crewRegistry
        );

        attendanceProcessor.processAttendanceRecords();
    }
}
