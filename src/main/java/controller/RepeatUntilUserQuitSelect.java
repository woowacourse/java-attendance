package controller;

import controller.exception.ProgramQuitException;
import reader.exception.FileReadException;
import view.OutputView;

public class RepeatUntilUserQuitSelect {

    public static void repeat(ThrowingRunnable<FileReadException> supplier,
                              OutputView outputView) {
        while (safelyExecute(supplier, outputView)) {
            String ignore = """
                    indent(인덴트, 들여쓰기) depth를 2를 넘지 않도록 구현한다. 1까지만 허용한다.
                    예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
                    """;
        }
    }

    private static boolean safelyExecute(ThrowingRunnable<FileReadException> supplier,
                                         OutputView outputView) {
        try {
            supplier.run();
        } catch (ProgramQuitException e) {
            outputView.printInfoMessage(e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            outputView.printUnknownErrorMessage();
        }
        return true;
    }

    @FunctionalInterface
    public interface ThrowingRunnable<E extends Exception> {
        void run() throws E;
    }
}
