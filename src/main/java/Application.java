import controller.AttendanceController;
import domain.Crews;
import loader.AttendanceHistoryLoader;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        AttendanceHistoryLoader attendanceHistoryLoader = new AttendanceHistoryLoader();
        Crews crews = attendanceHistoryLoader.loadCrews();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendanceController attendanceController = new AttendanceController(crews, inputView, outputView);
        attendanceController.run();
    }
}
