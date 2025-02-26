package attendance;

import attendance.view.InputView;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Map<String, Runnable> options;
    private static final String QUIT_OPTION = "Q";

    static {
        options = new HashMap<>();
        options.put("1", Application::doAttendance);
        options.put("2", Application::modifyAttendance);
        options.put("3", Application::checkAttendanceHistory);
        options.put("4", Application::checkDangerousCrews);
        options.put(QUIT_OPTION, null);
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        String option = InputView.readOption(today);
        if (option.equals(QUIT_OPTION)) {
            return;
        }
        run(option);
    }

    private static void run(final String option) {
        Runnable function = options.getOrDefault(option, null);
        if (function == null) {
            throw new IllegalArgumentException("존재하지 않는 옵션입니다.");
        }
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
