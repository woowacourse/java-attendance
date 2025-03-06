package model;

import static constant.ErrorMessage.INVALID_OPTION_FORMAT;

import java.util.Arrays;

public enum Option {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    QUIT("Q"),
    ;

    private final String value;

    Option(String value) {
        this.value = value;
    }

    public static Option find(String value) {
        return Arrays.stream(Option.values())
                .filter(option -> option.value.equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_OPTION_FORMAT.getMessage()));
    }
}
