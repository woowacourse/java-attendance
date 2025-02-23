package util;

import java.util.Optional;
import java.util.function.Supplier;
import view.OutputView;

public class RepeatExecutor {

    public static final String SUCCESS = "성공";
    private final OutputView outputView;

    public RepeatExecutor(OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T repeatUntilSuccess(Supplier<T> supplier) {
        Optional<T> result;
        do {
            result = checkSuccess(supplier);
        } while (result.isEmpty());

        return result.get();
    }

    private <T> Optional<T> checkSuccess(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return Optional.empty();
        }
    }
}
