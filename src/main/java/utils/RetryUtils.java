package utils;

import java.util.function.Supplier;
import view.output.OutputView;

public class RetryUtils {
    public static <T> T retryUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.displayErrorMessage(e.getMessage());
            }
        }
    }
}