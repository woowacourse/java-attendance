package domain;

import java.util.List;

public class Function {
    private final String function;
    private static final List<String> functions = List.of("1", "2", "3", "4", "Q");

    public Function(String function) {
        validateFunction(function);
        this.function = function;
    }

    public boolean equals(String function) {
        return this.function.equals(function);
    }

    private void validateFunction(String function) {
        if (!functions.contains(function)) {
            throw new IllegalArgumentException("올바른 기능 입력이 아닙니다.");
        }
    }
}
