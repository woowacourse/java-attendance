import controller.AttendanceController;

public class AttendanceApplication {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        AttendanceController attendanceController = appConfig.controller();
        attendanceController.start();
    }
}
