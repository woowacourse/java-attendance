import controller.AttendanceController;
import view.InputView;
import view.OutputView;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();
        AttendanceController attendanceController = new AttendanceController(inputView, outputView);
        attendanceController.run();
    }
}
