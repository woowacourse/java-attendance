import controller.AttendanceController;
import domain.model.AttendanceTodayClock;
import domain.model.Campus;
import domain.model.CrewHistories;
import domain.model.CrewInitializer;
import domain.model.TodayClock;
import util.FileReader;
import view.InputValidator;
import view.InputView;
import view.ResultView;

public class Application {

    public static void main(String[] args) {
        Campus campus = new Campus();
        TodayClock todayClock = new AttendanceTodayClock();
        CrewInitializer crewInitializer = new CrewInitializer(campus, todayClock);
        CrewHistories crewHistories = crewInitializer.initialize(FileReader.readFile());

        AttendanceController controller = makeController(campus, todayClock);
        controller.start(crewHistories);
    }

    private static AttendanceController makeController(final Campus campus, final TodayClock todayClock) {
        InputView inputView = new InputView(new InputValidator());
        ResultView resultView = new ResultView();
        return new AttendanceController(inputView, resultView, campus, todayClock);
    }
}
