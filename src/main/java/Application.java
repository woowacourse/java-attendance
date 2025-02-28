import controller.AttendanceController;
import view.InputView;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        AttendanceController attendanceController = new AttendanceController(inputView);
    }
}
