import controller.AttendanceCommandController;

public class Application {

    public static void main(String[] args) {
        final AttendanceCommandController attendanceCommandController = new AttendanceCommandController();
        attendanceCommandController.run();
    }
}
