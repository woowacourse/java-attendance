package attendance.controller;

import java.util.function.Supplier;

import attendance.view.ResultView;

public class RetryHandler {

    private RetryHandler() {}

    public static <T> T retryUntilSuccessWithReturn(final Supplier<T> supplier, final ResultView resultView) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }
    }

    public static void retryUntilSuccess(final Runnable runnable, final ResultView resultView) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (IllegalArgumentException e) {
                resultView.printErrorMessage(e.getMessage());
            }
        }
    }

}
