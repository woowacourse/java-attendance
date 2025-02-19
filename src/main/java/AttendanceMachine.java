import controller.AttendanceController;
import converter.StringConverter;
import view.InputView;
import view.OutputView;

public class AttendanceMachine {

    public static void main(String[] args) {
        new AttendanceController(new InputView(), new OutputView(), new StringConverter()).run();
    }
}
