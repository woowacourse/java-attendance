package exception;

import java.util.Optional;
import java.util.function.Supplier;
import view.OutputView;

public class ExceptionHandler {

    public static <T> T repeatUntilSuccess(Supplier<T> supplier) {
        Optional<T> result = Optional.empty();
        while (result.isEmpty()) {
            result = getSuccessResult(supplier, result);
        }
        return result.get();
    }

    private static <T> Optional<T> getSuccessResult(Supplier<T> supplier, Optional<T> result) {
        try {
            result = Optional.ofNullable(supplier.get());
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
        }
        return result;
    }
}
