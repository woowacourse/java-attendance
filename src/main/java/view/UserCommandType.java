package view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum UserCommandType {
    ATTENDANCE_CHECK("1"),
    ATTENDANCE_CHANGE("2"),
    ATTENDANCE_SHOW("3"),
    ALERT_CREW_CHECK("4"),
    QUIT("Q"),
    ;

    final String CommandKey;

    UserCommandType(String commandKey) {
        CommandKey = commandKey;
    }

    public static UserCommandType getUserCommand(String userCommand) {
        validateUserCommand(userCommand);
        return getCommandTypes().get(userCommand);
    }

    public static void validateUserCommand(String userCommand) {
        if (!containKey(userCommand)) {
            throw new IllegalArgumentException("올바른 기능 입력이 아닙니다.");
        }
    }

    private static boolean containKey(String userCommand) {
        return Arrays.stream(UserCommandType.values())
                .map(UserCommandType::getCommandKey)
                .toList()
                .contains(userCommand);
    }

    private static Map<String, UserCommandType> getCommandTypes() {
        Map<String, UserCommandType> types = new HashMap<>();

        for (UserCommandType userCommandType : UserCommandType.values()) {
            types.put(userCommandType.getCommandKey(), userCommandType);
        }
        return types;
    }

    private String getCommandKey() {
        return CommandKey;
    }
}
