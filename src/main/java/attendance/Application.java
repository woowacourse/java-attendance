package attendance;

import attendance.view.InputView;
import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        String option = InputView.readOption(today);
    }
}
