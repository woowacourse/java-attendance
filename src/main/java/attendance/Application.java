package attendance;

import attendance.domain.CampusManager;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Map<String, Runnable> options;
    private static final String QUIT_OPTION = "Q";
    private static final CampusManager campusManager = new CampusManager();

    static {
        options = new HashMap<>();
        options.put("1", Application::doAttendance);
        options.put("2", Application::modifyAttendance);
        options.put("3", Application::checkAttendanceHistory);
        options.put("4", Application::checkDangerousCrews);
        options.put(QUIT_OPTION, null);
    }

    public static void main(String[] args) {
        while (true) {
            LocalDate today = LocalDate.now();
            String option = InputView.readOption(today);
            if (option.equals(QUIT_OPTION)) {
                break;
            }
            try {
                run(option);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void run(final String option) {
        Runnable function = options.getOrDefault(option, null);
        if (function == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 옵션입니다.");
        }
        function.run();
    }

    private static void doAttendance() {
        LocalDate today = now();
        boolean isOperationDate = campusManager.isOperationDate(today);
        if (!isOperationDate) {
            OutputView.printNotOperationDate(today);
            return;
        }
        String crewNickname = InputView.readCrewNickname();
    }

    private static void modifyAttendance() {

    }

    private static void checkAttendanceHistory() {

    }

    private static void checkDangerousCrews() {

    }

    private static LocalDate now() {
        return LocalDate.now();
    }
}
