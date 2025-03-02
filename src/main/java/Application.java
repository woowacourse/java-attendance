import controller.AttendanceController;
import infrastructure.AttendanceFileReader;
import infrastructure.SystemDateProvider;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceController attendanceController = new AttendanceController(inputView, outputView);

        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();
        attendanceController.run(attendanceFileReader, new SystemDateProvider());
    }
}
