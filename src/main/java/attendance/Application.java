package attendance;

import attendance.controller.AttendanceController;
import attendance.service.AttendanceService;
import attendance.service.DateGeneratorImpl;
import attendance.utils.AttendanceFileParser;
import attendance.utils.ErrorUtils;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(
            new InputView(),
            new OutputView(),
            new AttendanceService(new AttendanceFileParser("src/main/java/resources/attendances.csv")),
            () -> LocalDate.of(2024,12,13)
        );

        ErrorUtils.executeWithError(controller::run);
    }
}
