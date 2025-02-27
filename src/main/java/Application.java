import config.AppConfig;
import controller.AttendanceController;

public class Application {
    public static void main(String[] args){
        AttendanceController attendanceController = AppConfig.INSTANCE.createAttendanceController();
        attendanceController.start();
    }
}
