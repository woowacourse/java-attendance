import controller.AttendanceController;
import controller.FileController;
import domain.AttendanceManager;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        AttendanceManager attendanceManager = new AttendanceManager();
        FileController fileController = new FileController(attendanceManager);
        AttendanceController attendanceController = new AttendanceController
            (new InputView(), new OutputView(),attendanceManager);

        fileController.initializeFile("src/main/resources/attendances.csv");
        attendanceController.run();
    }
}
