package constant;

import java.util.Arrays;

public enum MenuOption {
    ATTENDANCE_REGISTER("1"),
    ATTENDANCE_MODIFY("2"),
    CREW_ATTENDANCE_CHECK("3"),
    EXPULSION_RISK("4"),
    QUIT("Q");

    private final String inputMenuValue;

    MenuOption(String inputMenuValue) {
        this.inputMenuValue = inputMenuValue;
    }

    public static MenuOption validateSelectMenuOption(String input) {
        return Arrays.stream(MenuOption.values())
                .filter(option -> option.getInputMenuValue().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 선택입니다." + input));
    }

    public String getInputMenuValue() {
        return inputMenuValue;
    }
}
