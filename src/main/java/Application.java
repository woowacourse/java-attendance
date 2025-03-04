import controller.AttendanceController;
import java.time.LocalDate;
import java.util.Scanner;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(LocalDate.of(2024, 12, 13),
                new InputView(new Scanner(System.in)), new OutputView());
        controller.run();
    }
}
