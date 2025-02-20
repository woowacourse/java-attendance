package attendance;

import attendance.controller.AttendanceMachine;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceMachine machine = new AttendanceMachine(inputView, outputView);
        try {
            machine.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
