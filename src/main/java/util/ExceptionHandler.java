package util;

import java.util.function.Supplier;


public class ExceptionHandler {
    
    public static <T> T retryUntilSuccessWithReturn(Supplier<T> supplier) {
        ExecuteResult executeResult;
        do {
            executeResult = executeGivenMethod(supplier);
        } while (!executeResult.isSuccess());
        return (T) executeResult.result();
    }

    public static void retryUntilSuccess(Runnable runnable) {
        while (!executeGivenMethod(runnable))
            ;
    }

    private static <T> ExecuteResult executeGivenMethod(Supplier<T> supplier) {
        try {
            return new ExecuteResult(supplier.get(), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ExecuteResult(null, false);
    }

    private static boolean executeGivenMethod(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void printErrorMessageWithoutExitProgram(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    record ExecuteResult(Object result, boolean isSuccess) {
    }
}
