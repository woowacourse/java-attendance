package domain;

import java.util.Arrays;

public enum MenuOption {
    CHECK_ATTENDANCE("1"),
    CHANGE_ATTENDANCE("2"),
    SHOW_CREW_ATTENDANCE("3"),
    SHOW_RISK_OF_EXPELLED_CREWS("4"),
    QUIT("Q");

    final String keyOption;

    MenuOption(String keyOption) {
        this.keyOption = keyOption;
    }

    public static MenuOption selectOption(String option) {
        return Arrays.stream(MenuOption.values())
                .filter(optionOption -> optionOption.keyOption.equals(option))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 번호를 입력해주세요"));
    }
}
