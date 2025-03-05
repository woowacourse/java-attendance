import controller.AttendanceCheckConsumer;
import controller.AttendanceController;
import controller.AttendanceHistoryConsumer;
import controller.AttendanceModifyConsumer;
import controller.DismissalCrewCheckConsumer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import model.AttendanceBook;
import util.CsvFileReader;
import util.Initializer;
import view.CommandInputView;
import view.InputValidator;
import view.InputView;
import view.ResultView;

public class AttendanceApplication {

    public static void main(String[] args) {
        AttendanceController controller = createController();
        controller.start(loadAttendanceBook());
    }

    private static AttendanceController createController() {
        Scanner scanner = new Scanner(System.in);
        InputValidator inputValidator = new InputValidator();
        CommandInputView commandInputView = new CommandInputView(scanner, inputValidator);
        InputView inputView = new InputView(scanner, inputValidator);
        ResultView resultView = new ResultView();

        List<BiConsumer<AttendanceBook, LocalDate>> consumers = createConsumers(inputView, resultView);
        return new AttendanceController(commandInputView, consumers);
    }

    private static List<BiConsumer<AttendanceBook, LocalDate>> createConsumers(
            final InputView inputView,
            final ResultView resultView
    ) {
        return new ArrayList<>(List.of(
                new AttendanceCheckConsumer(inputView, resultView),
                new AttendanceModifyConsumer(inputView, resultView),
                new AttendanceHistoryConsumer(inputView, resultView),
                new DismissalCrewCheckConsumer(resultView)
        ));
    }

    private static AttendanceBook loadAttendanceBook() {
        List<String> csvLines = CsvFileReader.readAllLines();
        return new AttendanceBook(
                Initializer.loadAttendanceBook(csvLines)
        );
    }
}
