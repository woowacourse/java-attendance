package util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class RepeatExecutor {

    public static void repeatUntilSuccess(Runnable processInput, Consumer<String> processIfErrorOccur) {
        while (true) {
            try {
                processInput.run();
                break;
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }

    public static <T, U> void repeatUntilSuccess(BiConsumer<T, U> processInput, Consumer<String> processIfErrorOccur, T t, U u) {
        while (true) {
            try {
                processInput.accept(t, u);
                break;
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }

    public static <T, U> U repeatUntilSuccess(Function<T, U> processInput, Consumer<String> processIfErrorOccur, T input) {
        while (true) {
            try {
                return processInput.apply(input);
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }

    public static <T, U, R> R repeatUntilSuccess(BiFunction<T, U, R> processInput, Consumer<String> processIfErrorOccur, T inputT, U inputU) {
        while (true) {
            try {
                return processInput.apply(inputT, inputU);
            } catch (IllegalArgumentException e) {
                processIfErrorOccur.accept(e.getMessage());
            }
        }
    }
}
