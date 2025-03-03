import domain.command.AttendanceCommandHandler;
import controller.AttendanceController;
import reader.AttendanceFileReader;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();
        AttendanceCommandHandler attendanceCommandHandler = new AttendanceCommandHandler();

        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        AttendanceController attendanceController = new AttendanceController(
                inputView,
                outputView,
                attendanceCommandHandler);
        attendanceController.run(attendanceFileReader, AttendanceFileReader.ATTENDANCE_FILE_PATH);
    }
}
