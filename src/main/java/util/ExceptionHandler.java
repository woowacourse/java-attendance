package util;

import java.util.function.Supplier;

public class ExceptionHandler {
    public static <T> T retryUntilSuccessWithReturn(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void printErrorMessageWithoutExitSystem(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
