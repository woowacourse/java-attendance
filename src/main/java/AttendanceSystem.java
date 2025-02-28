import controller.AttendanceController;
import model.date.December;
import view.InputView;
import view.OutputView;

public class AttendanceSystem {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController(
                new OutputView(),
                new InputView(),
                December.now()
        );
        attendanceController.start();
    }
}
