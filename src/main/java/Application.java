import controller.MainController;
import domain.DateProvider;
import java.time.LocalDate;
import view.InputView;
import view.OutputView;

public class Application {

    private static final int YEAR = 2024;
    private static final int MONTH = 12;
    private static final int TODAY = LocalDate.now().getDayOfMonth();

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DateProvider dateProvider = DateProvider.from(YEAR, MONTH, TODAY);

        MainController mainController = new MainController(inputView, outputView, dateProvider);

        mainController.run();
    }
}
