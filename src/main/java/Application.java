import controller.AttendanceManagementController;
import java.time.LocalDate;
import model.TodayDate;

public class Application {
    public static void main(String[] args) {
        AttendanceManagementController controller = new AttendanceManagementController(new TodayDate(LocalDate.of(2024, 12, 12
        )));
        controller.start();
    }
}
