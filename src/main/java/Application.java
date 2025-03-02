import controller.AttendanceController;
import domain.Attendances;
import domain.AttendancesLoader;
import domain.TimeMachine;
import view.InputView;
import view.OutputView;

import java.io.FileReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        AttendancesLoader attendancesLoader = new AttendancesLoader();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        TimeMachine.timeTravelAt(inputView.readTodayDate());
        Attendances attendances = attendancesLoader.load(new FileReader("src/main/resources/attendances25_2.csv"));

        AttendanceController controller = new AttendanceController(inputView, outputView, attendances);
        controller.run();
    }
}
