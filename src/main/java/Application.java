import controller.AttendanceController;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final InputView inputView = new InputView(scanner);
        final OutputView outputView = new OutputView();
        AttendanceController attendanceController = new AttendanceController(inputView, outputView);
        attendanceController.run();
    }
}
