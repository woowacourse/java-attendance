package util;

import java.util.function.Function;
import java.util.function.Supplier;
import view.OutputView;

public class LoopTemplate {

    private LoopTemplate() {
    }

    public static <T> T tryCatchLoop(final Supplier<T> callback, final OutputView outputView) {
        try {
            return callback.get();
        } catch (final IllegalArgumentException | IllegalStateException e) {
            outputView.printExceptionMessage(e.getMessage());
            return tryCatchLoop(callback, outputView);
        }
    }

    public static <T, R> R tryCatchLoop(final Function<T, R> callback, final T data, final OutputView outputView) {
        try {
            return callback.apply(data);
        } catch (final IllegalArgumentException | IllegalStateException e) {
            outputView.printExceptionMessage(e.getMessage());
            return tryCatchLoop(callback, data, outputView);
        }
    }
}
