package attendance;

import attendance.domain.AttendanceDateTime;
import attendance.domain.SystemDateTime;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    public static void main(String[] args) {
        try (var input = new InputView()) {
            var output = new OutputView();
            SystemDateTime systemDateTime = new AttendanceDateTime();
            var controller = new AttendanceController(input, output, systemDateTime);

            controller.run();
        }
    }
}
