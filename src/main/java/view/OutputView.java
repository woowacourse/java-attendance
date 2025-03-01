package view;

public class OutputView {

    public static final String ERROR_MESSAGE_FORMAT = "[ERROR] %s%n";

    public void printErrorMessage(RuntimeException e) {
        printEmptyLine();
        System.out.printf(ERROR_MESSAGE_FORMAT, e.getMessage());
    }

    private void printEmptyLine() {
        System.out.println();
    }
}
