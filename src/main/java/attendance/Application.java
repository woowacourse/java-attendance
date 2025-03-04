package attendance;

import attendance.controller.Controller;
import attendance.model.AttendanceRegister;
import attendance.model.CrewDataLoader;
import attendance.view.InputView;
import attendance.view.OutputView;

public class Application {

    private static final AttendanceRegister attendanceRegister = new AttendanceRegister();
    public static final String FILE_NAME = "attendances.csv";

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Controller controller = new Controller(inputView, outputView, attendanceRegister);
        CrewDataLoader crewDataLoader = new CrewDataLoader(attendanceRegister);
        crewDataLoader.load(FILE_NAME);
        controller.run();
    }
}
