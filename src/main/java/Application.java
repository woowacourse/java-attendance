import controller.AttendanceController;
import model.Campus;
import model.CrewHistories;
import model.Initializer;
import util.FileReader;
import view.InputValidator;
import view.InputView;
import view.ResultView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView(new InputValidator());
        ResultView resultView = new ResultView();
        Campus campus = new Campus();
        Initializer initializer = new Initializer(campus);
        CrewHistories crewHistories = initializer.initialize(FileReader.readFile());

        AttendanceController controller = new AttendanceController(inputView, resultView, campus);
        controller.start(crewHistories);
    }
}
