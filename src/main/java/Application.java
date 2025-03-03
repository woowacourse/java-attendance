import controller.AttendanceController;
import infrastructure.AttendanceFileReader;
import infrastructure.DecemberDateProvider;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputView inputView = new InputView(scanner);
        OutputView outputView = new OutputView();

        AttendanceController attendanceController = new AttendanceController(inputView, outputView);

        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();
        attendanceController.run(attendanceFileReader, new DecemberDateProvider());
    }
}
