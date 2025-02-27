import controller.AttendanceController;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        AttendanceController attendanceController = new AttendanceController();
        attendanceController.run();
    }
}
