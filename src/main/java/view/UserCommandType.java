package view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum UserCommandType {
    ATTENDANCE_MARK("출석 확인", "1"),
    ATTENDANCE_CHANGE("출석 수정", "2"),
    SHOWING_ATTENDANCE("크루별 출석 기록 확인", "3"),
    SHOWING_ALERT_CREWS("제적 위험자 확인", "4"),
    QUIT("종료", "Q"),
    INITIAL("기본", "-1");

    private final String name;
    private final String commandCode;

    UserCommandType(String name, String commandCode) {
        this.name = name;
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
                .contains(userInput) || userInput.equals(INITIAL.getCommandCode())) {
            throw new IllegalArgumentException("올바른 기능 입력이 아닙니다.");
        }
    }
}
