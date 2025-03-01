package attendance.util;

import attendance.view.OutputView;

public class RetryHandler {

    public static void retryIfFailuare(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            OutputView.exception(e);
            retryIfFailuare(action);
        }
    }
}
