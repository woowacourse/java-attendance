package attendance;

import attendance.view.InputView;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Map<String, Runnable> options;

    static {
        options = new HashMap<>();
        options.put("1", Application::doAttendance);
        options.put("2", Application::modifyAttendance);
        options.put("3", Application::checkAttendanceHistory);
        options.put("4", Application::checkDangerousCrews);
        options.put("Q", null);
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        String option = InputView.readOption(today);
        Runnable function = options.get(option);
        function.run();
    }

    private static void doAttendance() {

    }

    private static void modifyAttendance() {

    }

    private static void checkAttendanceHistory() {

    }

    private static void checkDangerousCrews() {

    }
}
