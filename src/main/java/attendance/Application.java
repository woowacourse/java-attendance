package attendance;

import attendance.controller.AttendanceController;
import attendance.loader.AttendanceAssembler;
import attendance.loader.AttendancesLoader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {
    public static void main(String[] args) {

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceAssembler assembler = new AttendanceAssembler(new AttendancesLoader());

        AttendanceController controller = new AttendanceController(inputView, outputView, assembler);

        controller.run();

    }
}
