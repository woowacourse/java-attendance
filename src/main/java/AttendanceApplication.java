import controller.AttendanceController;
import domain.AttendanceBook;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();

        AttendanceBook attendanceBook = AttendanceBook.of(List.of());
        AttendanceController controller = new AttendanceController(
                attendanceBook,
                inputView,
                outputView
        );

        controller.run();
    }
}
