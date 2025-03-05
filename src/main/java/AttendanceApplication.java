import controller.ApplicationController;
import controller.AttendanceController;
import domain.AttendanceBook;
import view.ConsoleInputView;
import view.ConsoleOutputView;

public class AttendanceApplication {

    public static void main(String[] args) {
        final ConsoleInputView consoleInputView = new ConsoleInputView();
        final ConsoleOutputView consoleOutputView = new ConsoleOutputView();
        final AttendanceBook attendanceBook = AttendanceBook.create();
        final AttendanceController attendanceController = new AttendanceController(consoleInputView, consoleOutputView,
                attendanceBook);
        new ApplicationController(attendanceController).run();
    }
}
