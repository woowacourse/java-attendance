package model;

import static constant.ErrorMessage.INVALID_OPTION_FORMAT;

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
        for (Option option : Option.values()) {
            if (option.value.equals(value.toUpperCase())) {
                return option;
            }
        }
        throw new IllegalArgumentException(INVALID_OPTION_FORMAT.getMessage());
    }
}
