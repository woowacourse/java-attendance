import controller.AttendanceController;
import view.InputView;
import view.OutputView;

public class Application {

    private static final String ATTENDANCE_FILE_NAME = "/attendances.csv";

    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(ATTENDANCE_FILE_NAME, new OutputView(),
                new InputView());
        attendanceController.start();
    }
}
