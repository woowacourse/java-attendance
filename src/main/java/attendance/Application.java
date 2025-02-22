package attendance;

import attendance.controller.Controller;
import attendance.model.Crews;
import attendance.model.FixedCustomClock;
import attendance.model.loader.CrewDataLoader;
import attendance.model.loader.CrewRegistry;
import attendance.model.loader.RawAttendanceStore;
import attendance.service.AttendanceProcessor;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

public class Application {

    public static void main(String[] args) {
        Crews crews = new Crews(new ArrayList<>());
        FixedCustomClock fixedCustomClock = createCustomClock();
        RawAttendanceStore rawAttendanceStore = new RawAttendanceStore();
        CrewDataLoader crewDataLoader = new CrewDataLoader(rawAttendanceStore);
        CrewRegistry crewRegistry = new CrewRegistry(crews);
        AttendanceProcessor attendanceProcessor = new AttendanceProcessor(crewDataLoader, crewRegistry,
                fixedCustomClock, crews, rawAttendanceStore);

        attendanceProcessor.processAttendanceRecords();

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
}
