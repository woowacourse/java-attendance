package attendance;

import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    public static void main(String[] args) {
        try (var input = new InputView()) {
            var output = new OutputView();
            var controller = new AttendanceController(input, output);

            controller.run();
        }
    }
}
