package util;

import static controller.AttendanceController.QUIT;

import java.util.function.Supplier;

public class ExceptionHandler {
    public static <T> void runInputCommand(final Supplier<T> supplier) {
        while (!isQuitCommand(supplier)) {
            executeCommand(supplier);
        }
    }

    private static <T> boolean isQuitCommand(final Supplier<T> supplier) {
        return executeCommand(supplier);
    }

    private static <T> boolean executeCommand(final Supplier<T> supplier) {
        try {
            return supplier.get() == QUIT;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}