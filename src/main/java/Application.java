import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceMachine machine = new AttendanceMachine(inputView, outputView);
        LocalDate now = LocalDate.of(2025, 2, 17);
        machine.start(now);
    }
}
