import controller.AttendanceController;
import infrastructure.AttendanceFileReader;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final InputView inputView = new InputView(scanner);
        final OutputView outputView = new OutputView();

        final AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        final AttendanceController attendanceController = new AttendanceController(
                inputView, outputView);
        attendanceController.run(attendanceFileReader);

        scanner.close();
    }
}
