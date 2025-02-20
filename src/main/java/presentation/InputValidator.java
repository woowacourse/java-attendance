package presentation;

public class InputValidator {
    public static void commandValidate(String command) {
        if (!command.equals("1") && !command.equals("2") && !command.equals("3") && !command.equals("4")
                && !command.equals("Q") && !command.equals("q")) {
            throw new IllegalArgumentException("");
        }
    }
}
