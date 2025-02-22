import controller.AttendanceController;
import view.InputView;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        AttendanceController attendanceController = new AttendanceController(new InputView(new Scanner(System.in)));
        attendanceController.start();
    }
}
