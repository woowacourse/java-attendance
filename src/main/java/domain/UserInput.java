package domain;

import java.util.Arrays;

public enum UserInput {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    TOTAL_RECORDS_BY_CREW("3"),
    CHECK_PENALTY("4"),
    QUIT("Q");

    private final String input;

    UserInput(String input) {
        this.input = input;
    }

    public static UserInput getByInput(String input) {
        return Arrays.stream(UserInput.values())
                .filter(userInput -> userInput.getInput().equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR]"));
    }

    public String getInput() {
        return input;
    }
}