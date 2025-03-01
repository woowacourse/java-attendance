import controller.AttendanceController;
import java.time.LocalDate;
import java.util.Scanner;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(LocalDate.of(2024, 12, 6), new InputView(new Scanner(System.in)));
        controller.run();
    }
}
