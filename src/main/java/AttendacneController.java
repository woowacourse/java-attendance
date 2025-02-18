import java.util.List;

public class AttendacneController {
    private final InputView inputView;

    public AttendacneController(final InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        List<String> students = File.readFile("src/main/resources/attendances.csv");
        inputView.readCommand();
    }
}
