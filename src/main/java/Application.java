import controller.AttendanceController;
import domain.AttendanceBook;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AttendanceController attendanceController = new AttendanceController(new InputView(scanner), new OutputView(),
                new AttendanceBook());

        attendanceController.runSystem();
    }
}
