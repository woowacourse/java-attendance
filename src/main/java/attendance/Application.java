package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.CampusScheduler;
import attendance.domain.CrewHistories;
import attendance.domain.CrewInitializer;
import attendance.view.FileReader;
import attendance.util.StringParser;
import attendance.view.InputView;
import attendance.view.ResultView;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

public class Application {

    private static final String FILE_NAME = "src/main/resources/attendances.csv";
    private static final LocalDateTime today = LocalDateTime.of(2024, 12, 13, 10, 0);
    private static final String ZONE_ID = "Asia/Tokyo";

    public static void main(String[] args) throws IOException {
        CrewHistories crewHistories = initialize(FileReader.readFromSecondLine(FILE_NAME));

        InputView inputView = new InputView();
        ResultView resultView = new ResultView();
        Clock clock = Clock.fixed(today.toInstant(ZoneOffset.UTC), ZoneId.of(ZONE_ID));
        CampusScheduler campusScheduler = new CampusScheduler();
        AttendanceController controller = new AttendanceController(inputView, resultView, clock, campusScheduler);
        controller.run(crewHistories);
    }

    private static CrewHistories initialize(final List<String> inputs) {
        Map<String, List<LocalDateTime>> result = StringParser.parseFile(inputs);
        CrewInitializer crewInitializer = new CrewInitializer(result);
        return crewInitializer.initialize();
    }
}
