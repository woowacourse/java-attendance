package attendance.domain.constant;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum Function {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    QUIT("Q");

    private final String number;

    Function(final String number) {
        this.number = number;
    }

    public static Function of(String function) {
        return Arrays.stream(Function.values())
                .filter(func -> func.number.equals(function))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.FUNCTION_NOT_PRESENCE));
    }
}
