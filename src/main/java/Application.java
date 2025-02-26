import controller.Controller;
import java.time.LocalDate;
import model.TodayDate;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller(new TodayDate(LocalDate.of(2024, 12, 14)));
        controller.start();
    }
}
