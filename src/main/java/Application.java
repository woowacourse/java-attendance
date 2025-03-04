import controller.AttendanceController;
import domain.AttendancesLoader;
import domain.TimeMachine;
import view.InputView;
import view.OutputView;

import java.io.FileReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        AttendancesLoader attendancesLoader = new AttendancesLoader(new FileReader("src/main/resources/attendances25_2.csv"));

        TimeMachine.timeTravelAt(inputView.readTodayDate());
        AttendanceController controller = new AttendanceController(inputView, outputView, attendancesLoader);

        controller.run();
    }
}
