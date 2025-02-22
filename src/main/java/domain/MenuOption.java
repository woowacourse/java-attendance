package domain;

import java.util.List;

public class MenuOption {
    private static final List<String> menuOptions = List.of("1", "2", "3", "4", "Q");
    private final String menuOption;

    public MenuOption(String menuOption) {
        validateMenuOption(menuOption);
        this.menuOption = menuOption;
    }

    public boolean equals(String menuOption) {
        return this.menuOption.equals(menuOption);
    }

    public boolean isExit() {
        return menuOption.equals("Q");
    }

    private void validateMenuOption(String menuOption) {
        if (!menuOptions.contains(menuOption)) {
            throw new IllegalArgumentException("올바른 기능 입력이 아닙니다.");
        }
    }
}
