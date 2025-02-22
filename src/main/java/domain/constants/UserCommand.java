package domain.constants;

import java.util.Arrays;
import java.util.Objects;

public enum UserCommand {
    ADD_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    LOOKUP_CREW_ATTENDANCE("3"),
    LOOKUP_EXPULSION_CREWS("4"),
    QUIT("Q");

    private final String userInput;

    UserCommand(final String userInput) {
        this.userInput = userInput;
    }

    public static UserCommand of(final String userInput) {
        return Arrays.stream(UserCommand.values())
                .filter(userCommand -> isMatchTextCommand(userCommand, userInput))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_COMMAND_NOT_FOUND.getMessage()));
    }

    private static boolean isMatchTextCommand(final UserCommand userCommand, final String userInput) {
        return Objects.equals(userCommand.userInput, userInput);
    }

}
