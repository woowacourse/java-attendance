import controller.AttendanceController;
import domain.DateProvider;
import infrastructure.date.CustomDateProvider;
import infrastructure.file.AttendanceFileReader;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputView inputView = new InputView(scanner);
        OutputView outputView = new OutputView();

        DateProvider customDateProvider = new CustomDateProvider();
        AttendanceController attendanceController = new AttendanceController(
                inputView, outputView, customDateProvider);

        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();
        attendanceController.run(attendanceFileReader);
    }
}
