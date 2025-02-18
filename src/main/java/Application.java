import controller.AttendanceController;
import controller.FileController;
import repository.AttendanceRepository;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        FileController fileController = new FileController(attendanceRepository);
        AttendanceController attendanceController = new AttendanceController
            (new InputView(), new OutputView(),attendanceRepository);

        fileController.initializeFile("src/main/resources/attendances.csv");
        attendanceController.run();
    }
}
