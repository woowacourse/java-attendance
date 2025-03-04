package attendance.constant;

import attendance.util.ErrorMessage;

import java.util.Arrays;

public enum Option {

    RECORD("1"),
    EDIT("2"),
    CHECK_RECORD("3"),
    CHECK_PENALTY("4"),
    QUIT("Q")
    ;

    private final String number;

    Option(String number) {
        this.number = number;
    }

    public static boolean isQuit(String input) {
        return Arrays.stream(Option.values())
                .anyMatch(option -> input.equals(QUIT.number));
    }

    public static Option select(String input) {
        return Arrays.stream(Option.values())
                .filter(option -> !input.equals(QUIT.number))
                .filter(option -> input.equals(option.number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_INPUT_OPTION_ERROR.getMessage()));
    }
}
