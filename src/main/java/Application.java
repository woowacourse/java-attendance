import java.io.IOException;
import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        AttendanceMachine machine = new AttendanceMachine(inputView, outputView);
        LocalDate now = LocalDate.now();
        machine.start(now);
    }
}
