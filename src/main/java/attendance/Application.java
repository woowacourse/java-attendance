package attendance;

import attendance.controller.AttendanceController;
import attendance.controller.TodayGenerator;
import attendance.domain.AttendanceFileParser;
import attendance.domain.AttendanceManager;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller =
            new AttendanceController(
                new InputView(),
                new AttendanceManager(
                    new AttendanceFileParser("src/main/java/resources/attendances.csv")),
                new OutputView(),
                new TodayGenerator()
            );
        controller.run();
    }
}
