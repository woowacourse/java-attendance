import controller.AttendanceController;
import util.CsvReader;
import view.InputView;
import view.OutputView;

public class AppConfig {
    public AttendanceController controller() {
        return new AttendanceController(fileReader(), outputView(), inputView());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private CsvReader fileReader() {
        return new CsvReader();
    }
}
