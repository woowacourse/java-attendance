package util;

import java.util.function.Supplier;
import view.OutputView;

public class RepeatExecutor {
    private final OutputView outputView;

    public RepeatExecutor(OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T repeatUntilSuccess(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return repeatUntilSuccess(supplier);
        }
    }
}
