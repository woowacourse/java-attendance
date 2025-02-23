package util;

public class RepeatUntilUserQuitSelectUtil {

    @FunctionalInterface
    public interface ThrowingSupplier<T, E extends Exception> {
        T get() throws E;
    }

    public static void repeat(ThrowingSupplier<Boolean, FileReadException> supplier) {
        boolean shouldContinue = true;

        while (shouldContinue) {
            shouldContinue = safelyExecute(supplier);
        }
    }

    private static boolean safelyExecute(ThrowingSupplier<Boolean, FileReadException> supplier) {
        try {
            return handleExecution(supplier);
        } catch (FileReadException e) {
            System.out.println("[ERROR] 프로그램을 실행할 수 없습니다: " + e.getMessage());
            System.out.flush();
            return false;
        }
    }

    private static boolean handleExecution(ThrowingSupplier<Boolean, FileReadException> supplier) throws FileReadException {
        try {
            return !supplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR] 올바른 값을 입력해 주세요.");
        }
        return true;
    }
}