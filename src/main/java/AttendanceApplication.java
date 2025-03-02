import controller.AttendanceController;
import domain.AttendanceBook;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import util.ConsoleInputReader;
import util.CsvFileReader;
import util.FileReader;
import util.InputReader;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        InputReader inputReader = new ConsoleInputReader(new BufferedReader(new InputStreamReader(System.in)));
        InputView inputView = new InputView(inputReader);
        OutputView outputView = new OutputView();
        AttendanceBook attendanceBook = new AttendanceBook();
        FileReader fileReader = new CsvFileReader("src/main/resources/attendances.csv");
        AttendanceController attendanceController = new AttendanceController(attendanceBook, inputView, outputView,
                fileReader);
        attendanceController.start();
    }
}
