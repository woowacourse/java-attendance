package attendance;

import static attendance.exception.ErrorMessage.NOT_EXISTS_OPTION;

import java.util.HashMap;
import java.util.Map;

public class Options {
    private static final String QUIT_OPTION = "Q";

    private static final Map<String, Runnable> options;

    static {
        options = new HashMap<>();
        options.put("1", Application::doAttendance);
        options.put("2", Application::modifyAttendance);
        options.put("3", Application::checkAttendanceHistory);
        options.put("4", Application::checkDangerousCrews);
        options.put("Q", null);
    }

    private Options() {
    }

    public static boolean run(final String option) {
        if (isExitOption(option)) {
            return false;
        }
        Runnable function = getFunction(option);
        function.run();
        return true;
    }

    private static boolean isExitOption(final String option) {
        return option.equals(QUIT_OPTION);
    }

    private static Runnable getFunction(String option) {
        return options.getOrDefault(option, () -> {
            throw new IllegalArgumentException(NOT_EXISTS_OPTION.getMessage());
        });
    }
}
