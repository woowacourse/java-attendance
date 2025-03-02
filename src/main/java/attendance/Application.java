package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.AttendanceFileParser;
import attendance.domain.AttendanceManager;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller =
            new AttendanceController(
                new InputView(),
                new AttendanceManager(
                    new AttendanceFileParser("src/main/java/resources/attendances.csv")),
                new OutputView(),
                () -> LocalDate.of(2024, 12, 13)
            );
        controller.run();
    }
}
