import controller.AttendanceController;
import converter.StringConverter;
import view.InputView;

public class AttendanceMachine {

    public static void main(String[] args) {
        new AttendanceController(new InputView(), new StringConverter()).run();
    }
}
