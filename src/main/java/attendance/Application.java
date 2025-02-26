package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.model.AttendanceTodayClock;
import attendance.domain.model.Campus;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewInitializer;
import attendance.domain.model.TodayClock;
import attendance.util.FileReader;
import attendance.view.InputValidator;
import attendance.view.InputView;
import attendance.view.ResultView;

public class Application {

    public static void main(String[] args) {
        Campus campus = new Campus();
        TodayClock todayClock = new AttendanceTodayClock();
        CrewInitializer crewInitializer = new CrewInitializer(campus, todayClock);
        CrewHistories crewHistories = crewInitializer.initialize(FileReader.read());

        AttendanceController controller = makeController(campus, todayClock);
        controller.start(crewHistories);
    }

    private static AttendanceController makeController(final Campus campus, final TodayClock todayClock) {
        InputView inputView = new InputView(new InputValidator());
        ResultView resultView = new ResultView();
        return new AttendanceController(inputView, resultView, campus, todayClock);
    }
}
