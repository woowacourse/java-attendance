import controller.AttendanceController;
import model.DateGenerator;
import view.InputView;
import view.OutputView;

public class AttendanceSystem {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(
                new OutputView(),
                new InputView(),
                DateGenerator.now()
        );
        attendanceController.start();
    }
}
