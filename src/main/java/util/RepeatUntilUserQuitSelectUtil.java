package util;

import java.util.function.Supplier;

public class RepeatUntilUserQuitSelectUtil {

    public static void repeat(Supplier<Boolean> supplier) {
        while (true) {
            try {
                if (isExitCommand(supplier)) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
                throw e;
            }
        }
    }

    private static boolean isExitCommand(Supplier<Boolean> supplier) {
        return !supplier.get();
    }
}
