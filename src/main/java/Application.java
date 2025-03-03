import controller.AttendanceController;
import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        new AttendanceController().run(LocalDate.now());
    }
}
