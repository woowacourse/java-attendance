import controller.AttendanceController;
import controller.FileController;
import domain.AttendanceManager;

public class Application {

    public static void main(String[] args) {
        AttendanceManager attendanceManager = new AttendanceManager();

        FileController fileController = new FileController(attendanceManager);
        AttendanceController attendanceController = new AttendanceController(attendanceManager);

        fileController.initializeFile("src/main/resources/attendances.csv");
        attendanceController.run();
    }
}
