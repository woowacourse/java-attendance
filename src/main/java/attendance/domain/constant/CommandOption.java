package attendance.domain.constant;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum CommandOption {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    QUIT("Q");

    private final String number;

    CommandOption(final String number) {
        this.number = number;
    }

    public static CommandOption of(String function) {
        return Arrays.stream(CommandOption.values())
                .filter(func -> func.number.equals(function))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.FUNCTION_NOT_PRESENCE));
    }

}
