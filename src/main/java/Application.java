import controller.AttendanceController;
import controller.FileController;
import domain.Crews;

public class Application {

    public static void main(String[] args) {
        Crews crews = new Crews();

        FileController fileController = new FileController(crews);
        AttendanceController attendanceController = new AttendanceController(crews);

        fileController.initializeFile("src/main/resources/attendances.csv");
        attendanceController.run();
    }
}
