package util;

import java.util.function.Supplier;
import view.OutputView;

public class InputProcessor {

    private static final OutputView outputView = new OutputView();

    public static <T> T processInputUntilSuccess(Supplier<T> process) {
        while (true) {
            try {
                return process.get();
            } catch (IllegalArgumentException e) {
                this.outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
