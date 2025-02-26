package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.model.Campus;
import attendance.domain.model.CrewHistories;
import attendance.domain.model.CrewInitializer;
import attendance.util.FileReader;
import attendance.view.InputValidator;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Application {

    private static final Clock fixedClock = Clock.fixed(
            LocalDateTime.of(2024, 12, 13, 10, 0).toInstant(ZoneOffset.UTC),
            ZoneId.of("Asia/Tokyo"));

    public static void main(String[] args) {
        Campus campus = new Campus();
        CrewInitializer crewInitializer = new CrewInitializer(campus, fixedClock);
        CrewHistories crewHistories = crewInitializer.initialize(FileReader.read());

        AttendanceController controller = makeController(campus);
        controller.start(crewHistories);
    }

    private static AttendanceController makeController(final Campus campus) {
        InputView inputView = new InputView(new InputValidator());
        ResultView resultView = new ResultView();
        return new AttendanceController(inputView, resultView, campus, fixedClock);
    }
}
