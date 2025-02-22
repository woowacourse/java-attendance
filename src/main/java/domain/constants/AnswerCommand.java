package domain.constants;

import java.util.Arrays;
import java.util.Objects;

public enum AnswerCommand {
    YES("Y"),
    NO("N");

    private final String command;

    AnswerCommand(final String command) {
        this.command = command;
    }

    public static AnswerCommand of(final String command) {
        return Arrays.stream(AnswerCommand.values())
                .filter(answerCommand -> Objects.equals(answerCommand.command, command))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.USER_COMMAND_NOT_FOUND.getMessage()));
    }
}
