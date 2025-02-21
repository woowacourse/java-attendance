import controller.AttendanceController;
import service.CsvReader;
import view.InputView;
import view.OutputView;

public class AppConfig {
    public AttendanceController controller() {
        return new AttendanceController(csvReader(), outputView(), inputView());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private CsvReader csvReader() {
        return new CsvReader();
    }
}