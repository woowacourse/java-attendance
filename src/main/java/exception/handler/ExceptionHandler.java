package exception.handler;

import java.util.function.Supplier;

public class ExceptionHandler {

    public static <T> T retryIfIllegalArgumentAndReturn(Supplier<T> task) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); //TODO : outputView로 넘길 수 없을까?
            }
        }
    }

    public static void retryIfIllegalArgument(Runnable task) {
        while (true) {
            try {
                task.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); //TODO : outputView로 넘길 수 없을까?
            }
        }
    }
}
