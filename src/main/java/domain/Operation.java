package domain;

import java.util.Arrays;
import java.util.Objects;

public enum Operation {
    ADD_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    LOOKUP_CREW_ATTENDANCE("3"),
    LOOKUP_EXPULSION_CREWS("4"),
    QUIT("Q");

    String inputValue;

    Operation(final String inputValue) {
        this.inputValue = inputValue;
    }

    public static Operation of(final String inputValue) {
        return Arrays.stream(Operation.values())
                .filter(operation -> Objects.equals(operation.inputValue, inputValue))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
