import controller.AttendanceController;
import util.FileReaderUtil;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();

        AttendanceController attendanceController = new AttendanceController(
                FileReaderUtil.DEFAULT_ATTENDANCE_DATA_PATH,
                inputView,
                outputView);
        attendanceController.run();
    }
}
