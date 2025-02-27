package exception;

import java.util.function.Supplier;
import view.OutputView;

public class ExceptionHandler {

    public static <T> T repeatUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalStateException ignored) {
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
