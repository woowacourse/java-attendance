package view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum UserCommandType {
    ATTENDANCE_MARK("1"),
    ATTENDANCE_CHANGE("2"),
    SHOWING_ATTENDANCE("3"),
    SHOWING_ALERT_CREWS("4"),
    QUIT("Q");

    private final String commandCode;

    UserCommandType(String commandCode) {
        this.commandCode = commandCode;
    }

    private String getCommandCode() {
        return commandCode;
    }

    public static UserCommandType getCommand(String rawCommand) {
        validateInput(rawCommand);
        return getCommandTypes().get(rawCommand);

    }

    private static Map<String, UserCommandType> getCommandTypes() {
        Map<String, UserCommandType> types = new HashMap<>();

        for (UserCommandType userCommandType : UserCommandType.values()) {

            types.put(userCommandType.getCommandCode(), userCommandType);
        }
        return types;
    }

    public static void validateInput(String userInput) {
        if (!Arrays.stream(UserCommandType.values()).map(UserCommandType::getCommandCode).toList()
                .contains(userInput)) {
            throw new IllegalArgumentException("올바른 기능 입력이 아닙니다.");
        }
    }
}
