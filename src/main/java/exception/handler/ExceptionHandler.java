package exception.handler;

import java.util.function.Supplier;

public class ExceptionHandler {

    public static <T> T retryIfIllegalArgumentAndReturn(Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
