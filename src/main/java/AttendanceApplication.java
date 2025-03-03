import controller.AttendanceController;
import service.AttendanceService;

public class AttendanceApplication {
    public static void main(String[] args) {
        AttendanceController controller = new AttendanceController(
                new AttendanceService(true)
        );
        controller.run();
    }
}
