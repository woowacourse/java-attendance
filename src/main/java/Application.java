import controller.AttendanceController;
import java.time.LocalDate;
import view.InputView;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(
                LocalDate.of(2024, 12, 13),
                new InputView(new Scanner(System.in))
        );
        attendanceController.run();
    }
}
