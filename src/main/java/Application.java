import controller.AttendanceManagementController;
import controller.TodayDateGenerator;

public class Application {
    public static void main(String[] args) {
        TodayDateGenerator dateGenerator = new TodayDateGenerator();
        AttendanceManagementController controller = new AttendanceManagementController(dateGenerator);
        controller.start();
    }
}
