import controller.AttendanceController;
import java.util.Scanner;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final InputView inputView = new InputView(scanner);

        final AttendanceController attendanceController = new AttendanceController(inputView);
        attendanceController.run();

        scanner.close();
    }
}
