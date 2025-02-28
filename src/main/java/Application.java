import controller.AttendanceController;
import reader.AttendanceFileReader;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        final String attendanceFilePath = "src/main/resources/attendances.csv";

        InputView inputView = new InputView(new Scanner(System.in));
        OutputView outputView = new OutputView();

        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        AttendanceController attendanceController = new AttendanceController(inputView, outputView);
        attendanceController.run(attendanceFileReader, attendanceFilePath);
    }
}
