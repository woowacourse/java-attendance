import config.Config;
import controller.AttendanceController;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        AttendanceController attendanceController = config.attendanceController();
        attendanceController.start();
    }
}
