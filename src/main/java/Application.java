import controller.AttendanceManagementController;
import java.time.LocalDate;
import model.TodayDate;

public class Application {
    public static void main(String[] args) {
        AttendanceManagementController controller = new AttendanceManagementController();
        controller.start();
    }
}
