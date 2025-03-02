import controller.AttendanceController;
import view.ConsoleInputView;
import view.ConsoleOutputView;

public class AttendanceApplication {

    public static void main(String[] args) {

        final ConsoleInputView consoleInputView = new ConsoleInputView();
        final ConsoleOutputView consoleOutputView = new ConsoleOutputView();
        final AttendanceController attendanceController = new AttendanceController(consoleInputView, consoleOutputView);
        attendanceController.run();

    }
}
