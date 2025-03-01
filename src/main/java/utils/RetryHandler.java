package utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RetryHandler {

    private RetryHandler() {
    }

    public static <R> R retryUntilNotException(final Supplier<R> logic, final Consumer<String> output) {
        while (true) {
            try {
                return logic.get();
            } catch (final IllegalArgumentException | IllegalStateException e) {
                output.accept(e.getMessage());
            }
        }
    }

    public static <T, R> R retryUntilNotException(final Function<T, R> logic, final T data,
                                                  final Consumer<String> output) {
        while (true) {
            try {
                return logic.apply(data);
            } catch (final IllegalArgumentException | IllegalStateException e) {
                output.accept(e.getMessage());
            }
        }
    }
}
