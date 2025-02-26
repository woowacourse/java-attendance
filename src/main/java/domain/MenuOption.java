package domain;

public enum MenuOption {
    CHECK_ATTENDANCE("1"),
    MODIFY_ATTENDANCE("2"),
    CHECK_CREW_ATTENDANCE_HISTORY("3"),
    CHECK_DANGEROUS_CREW("4"),
    QUIT_REGEX("[Qq]")
    ;

    private final String menu;

    MenuOption(String menu) {
        this.menu = menu;
    }

    public String getValue() {
        return this.menu;
    }

    public static MenuOption getMenuOption(String menu) {
        for (MenuOption option : MenuOption.values()) {
            if (option.getValue().equals(menu)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid menu option: " + menu);
    }
}
