import controller.AttendanceManagementController;
import controller.TodayDateGenerator;
import java.time.LocalDate;
import model.TodayDate;

public class Application {
    public static void main(String[] args) {
        TodayDateGenerator dateGenerator = new TodayDateGenerator();
        AttendanceManagementController controller = new AttendanceManagementController(dateGenerator);
        controller.start();
    }
}
